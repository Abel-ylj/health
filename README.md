## 健康项目

## (零)概述

- 项目采用SOA架构(Servic Oriented Architec)
- 有后台管理，前台H5, 服务提供方，用dobbo RPC框架进行整合运行
- 用maven进行分模块开发，聚合部署(如图中，common模块为单独项目，最后该模块会被其余3个工程以依赖，3个工程各自构建时都会打包common.jar到自己的工程里去)

## (一)架构

![image-20210113161042779](https://tva1.sinaimg.cn/large/008eGmZEly1gmoly9o3lcj30zk0oeacg.jpg)



## (二)技术选型

### 分页插件

>PageHelper自动适配了页号(前端传过来currentPage=1, pageSize=10。拦截后织入会变成limit 0,10)
>
>使用ThreadLocal来存储当前请求的分页请求参数(currentPage, pageSize), 和分页结果参数(total,pages)
>
>Page<?> 对象创建时候会去threadlocal中获取分页的相关所有参数。

- PageHelper的Mybatis插件

- 在Mybatis的核心配置文件中注册插件

    ```xml
    <plugins>
      <!-- com.github.pagehelper 为 PageHelper 类所在包名 -->
      <plugin interceptor="com.github.pagehelper.PageHelper">
        <!-- 设置数据库类型 Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL 六种数据库-->
        <property name="dialect" value="mysql"/>
      </plugin>
    </plugins>
    ```

- 使用

    ```java
    //方式一(不推荐， 自己创建Page对象，创建时候会自动从threadlocal中拿分页数据)
    //这一句是将分页参数绑定到ThreadLocal中，将分页参数和当前线程绑定
    PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
    //执行查询时候，Pagehelper会拦截后动态增强 实际执行的是
    //1. select count(*) from t_checkitem; 获取total条数，将结果存到threadLocal
    //2. select * from t_checkitem limit currentPage, pageSize;
    List<Checkitem> list = checkitemMapper.selectAll();
    //分装PageHelper中的分页对象,其中的分页相关参数会从threadLocal中获取
    PageInfo<Checkitem> pi = new PageInfo<Checkitem>(list);
    
    //2. 方式二: 自动封装Page对象(原理就是从threadlocal中去拿total，currentPage，pageSize值)
    Page<Checkitem> pg = checkitemMapper.findPageByCondition(pb.getQueryString());
    return new PageResult(pg.getTotal(), pg.getResult());
    ```

    



## (三)业务



#### 后台检查项-检查组-套餐

- 预约管理模块

    1. 针对系统管理员： 以上三项进行增删改查。
    2. 针对用户： 预约管理(1. 已预约查看 2.可预约设置(每天可预约人数))

- H5和backend都调用service_provider在dubbo上发布的服务。

- 检查项管理，检查组管理，检查套餐管理。

    业务场景：用户选择1个检查套餐，如孕检套餐，那么1个孕检套餐下有多个检查组，如针对胎儿的唐氏筛查检查组，针对母体的孕期三高检查组等等，每个检查组中又细分为多个检查项，如唐氏筛查检查组中就需要有 1.hcg检查项 2. 苯丙酮检查项 3. 游离雌三醇检查项等等。。。

- 检查项的 增删改查， 重点是删除， **删除时要对检查项进行依赖检查**

- 检查组的 增删改查， 同检查项，删除时候要检查是否被套餐依赖

- 套餐 有附带图片(要给用户展示和选择)，图片上传采用阿里云oss。

    套餐 中有一个垃圾图片的处理(没有被套餐引用的图片)，处理方式如下：

    1. 维护两个set集合，一个存储已被引用的图片A。 另一个存储用户触发过上传的图片B.
    2. 集合B 包含 集合A
    3. 定时比对两个集合，将差集删除

    集合采用redis中的set数据结构(redis符合分布式共享存储)，定时器使用spring的cron

- 套餐图片管理

    1. H5前端工程 将图片传到 集合B中（用户历史上传的图片）
    2. 后端工程(数据库工程) 将图片名上传到 集合A中 (套餐实际引用的图片)
    3. cron工程(定时器工程) 定时扫描差集，将差集删除。

    

#### 定时删无效图片(quartz定时器)

- Quartz 的cron 和spring的cron 和 linux的cron 规则有一点不一样

- quartz cron 规则(秒分时日 月周年)

    ![image-20210119130746749](https://tva1.sinaimg.cn/large/008eGmZEly1gmsxapl34mj30qu0eg40c.jpg)

- 特殊字符规则

    `,` :  多值列表 如 在`月域`中 1，3，5 表示1月，3月，5月

    `-`:  范围 如在 `时域` 中 3-6表示 3点到6点(用值列表表示就是 3,4,5,6)

    `*`:   表示在该域中的所有合法值 如在`时域`上* 表示所有的时，用-表示 (0-23)

    ​		在秒域上表示每一秒

    `/`:  表示间隔  比如在 `秒域`上 0/15 表示从0秒开始，每隔15秒触发

    `?`:  只能用在日或周中,表示不指定

    ​		若用了 `日域`就将`周域`置位？， 

    ​		若用了 `周域`就将 `日域`置位?, 因为都是要表示某一天

    `#`: 只能用在`周域`上，如 *6#3* 表示 月的第三个周5

    `L`: 只能用在`日周上`，表示该域的最后一天。

    ​		用在了日域上，表示当月的最后一天

    ​		用在了周域上，表示当周的最后一天(周6)

    `W`: 只能用在`日域`上，表示指定日最近的工作日 周一到周五。

    ​       如在日域上指定 15W, 表示选择 15号最近的工作日(包含15日)，若15号是周6，则在17号触发

- 0/10 在秒域的解释，从0--分钟开始，每隔10分钟触发

    |0(开始触发)--1--2--3--4--...--9--| |10(打印)--11--12--...18--19--| |20(打印)--..

- 5/10 在秒域解释， 从第5秒开始，每隔10秒触发(开始时刻可选 0-59)

    |0--1--2--3--4--5(开始触发)--6--7--8--9--|  |10--11--12--13--14--15(触发)--16--17--18--19--|

- 0/3 在分域的解释，在0分开始执行，每隔3分钟触发(第三分钟开始触发)

    0--1--2--3.....--59, 按3分分组

    |0(开始执行)--1--2--|  | 3(触发)--4--5--| |6(触发)--7--8--

- 工具网站

    Quartz cron工具 生成器： https://cron.qqe2.com/ 用来生成cron

    生成器，验证器: https://tool.lu/crontab/  用上面网站产生的cron表达式，在这个网站验证

    同时，https://tool.lu/crontab/ 也支持 linux cron和 spring cron的表达式

#### POI & 日历组件

- 业务: 导医处用excel 登记用户的预约信息，然后将其导入到管理系统中

    ​		 后台用日历的形式展示每天的预约信息(预约用户量)

    ​		 在后台可以在日历控件中，设置每日的可预约上限人数。

    ​		 用户可以在手机端H5页面上预约登记，管理员可以在后台查看到预约信息。

- 技术： 

    1. poi可以操作excel, word, ppt等文件格式，而jxl只能操作xls
    2. 在预约设置页下载 excel的模板文件。(约定格式)
    3. 在模板文件中 填写完数据后 上传，后台程序自动解析，完成数据导入
    4. H5页面用calender控件 将预约列表展示给用户

#### 前台手机快速登录

- 业务流程

    手机获取验证码，后端校验验证码后，查询手机用户是否存在，

    若不存在就 自动注册，返回token(此处用的是用户手机号，应该用随机数，防止被猜测)，

    将用户对象存到redis

#### 前台手机端预约套餐

- 用户在套餐列表页面选择套餐
- 进入选择的套餐详情页，将1.预约信息(个人信息) 2.预约信息(选择的套餐) 3. 预约日期提交
- 后端需要校验
    1. 用户是否已经注册(自动注册)
    2. 用户是否已经在 预约日当天预约过该套餐(防抖，发送两次)
    3. 体检机构在当日的预约数量是否已经到达限额值。
- 校验通过，将预约信息记录，同时将预约数量调整
- 返回前端预约成功，前端页面跳转到预约成功页面，同时查询刚才的预约详情记录

#### **后台管理系统权限控制**

- 权限部分是该工程的重点

- 重要概念

    1. 认证：让系统知道你是谁
    2. 授权： 系统赋予你能看到什么，操作什么。

- wb模板工程的做法

    Privilidge ->Role -> Account

    权限是指后台管理系统的左侧菜单栏。菜单栏中的每一项都对应Privilidge表中的一行记录。

    且记录之前有父子关系(parentId指定)

    添加角色时，前端将{role, [p1,p2,p3,p4]}传入(p1,p2,p3,p4)，

    P1,p2,p3,p4由前端从后端查，树结构由前端自己构造，或后端构造

    ```java
    //构建权限树
    public List<AdminPrivilegeEntity> getTree() {
            List<AdminPrivilegeEntity> all = adminPrivilegeMapper.getAll();
            List<AdminPrivilegeEntity> rlst = new ArrayList<>();
            if (all != null && all.size() > 0){
                HashMap<Long, AdminPrivilegeEntity> map = new HashMap<>();
                for (AdminPrivilegeEntity p : all) {
                    if (p.getParentId() == 0){
                        rlst.add(p);
                    }
                    AdminPrivilegeEntity pv = map.get(p.getId());
                    if (pv != null){
                        p.setChildren(pv.getChildren());
                    }
                    map.put(p.getId(), p);
                    AdminPrivilegeEntity parent = map.computeIfAbsent(p.getParentId(), o -> new AdminPrivilegeEntity());
                    parent.getChildren().add(p);
                }
            }
            return rlst;
        }
    
    //sql 数据结构
    1	会员管理	/vip	0
    2	会员列表	/vip_list	1
    3	邀请关系	/vip_invite	1
    4	平台数据	/vip_data	1
    
    平台数据菜单的访问路径 是 项目路径/vip/vip_data
    ```

- health中权限表的逻辑结构

    在wb的模板工程中，没有实现role->permisson的权限部分，只实现了role-menu的部分
    
    ![image-20210121122229957](https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-21-043629.jpg)

- SpringSecurity
    1. 使用见demo工程



#### 后台菜单根据角色展示

- 根据用户的角色 动态查找菜单栏，完成菜单树的构造，返给前端
- 菜单树的构造，借用一个map集合，达到遍历一遍就构造完整棵树的效果。

#### 统计数据POI导出







