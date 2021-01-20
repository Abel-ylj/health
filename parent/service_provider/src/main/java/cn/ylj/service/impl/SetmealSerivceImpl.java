package cn.ylj.service.impl;

import cn.ylj.constant.MessageConstant;
import cn.ylj.constant.RedisConstant;
import cn.ylj.entity.*;
import cn.ylj.enums.OrderStatusEnum;
import cn.ylj.enums.OrderTypeEnum;
import cn.ylj.ex.EmptyException;
import cn.ylj.ex.FullException;
import cn.ylj.ex.ReplicateException;
import cn.ylj.mapper.*;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:25 下午
 */
@Service(interfaceClass = ISetMealService.class)
public class SetmealSerivceImpl implements ISetMealService {

    @Resource
    SetmealMapper setmealMapper;
    @Resource
    private JedisPool jedisPool;
    @Resource
    private CheckgroupMapper checkgroupMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void add(Setmeal setmeal, Integer[] ids) {
        setmealMapper.insert(setmeal);
        //插入关系
        setmealMapper.insertRel(setmeal.getId(), ids);
        //将该套餐附带的图片 添加到redis已被引用图片记录的set中

        //TODO 思考，redis和transcation的关系
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    public void delete(Integer id) {
        //查询是否已被预约，若已经被预约，则无法修改

    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void update(Setmeal setmeal, Integer[] ids) {

    }

    public Page<Setmeal> findPage(QueryPageBean pb) {
        PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
        Page<Setmeal> page = setmealMapper.findPage(pb.getQueryString());
        return page;
    }

    public Setmeal findOneById(Integer id) {
        return setmealMapper.selectByPrimaryKey(id);
    }

    public List<Integer> findRelBySetmealId(Integer id){
       Integer[] ids = setmealMapper.findRelBySetmealId(id);
       return Arrays.asList(ids);
    }

    @Override
    public List<Setmeal> getSetmealList() {
        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealMapper.findDetailById(id);
        if (setmeal.getCheckgroupList() != null && setmeal.getCheckgroupList().size() > 0){
            //将检查组对应的检查项 写入列表
            for (Checkgroup checkgroup : setmeal.getCheckgroupList()) {
                Checkgroup cg = checkgroupMapper.findByIdWithRel(checkgroup.getId());
                checkgroup.setCheckitemList(cg.getCheckitemList());
            }
        }
        return setmeal;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer order(Member member, Integer setmealId, Date orderDate) throws Exception {
        //1. 查询用户是否已存在,不存在新建member
        Member m = memberMapper.findCntByIdCard(member.getIdcard());
        if (m == null){
            member.setRegtime(new Date());
            memberMapper.insertSelective(member);
        } else {
            member.setId(m.getId());
        }
        //2. 查看预约日期的预约数量
        Ordersetting os = ordersettingMapper.findOneByDateForUpdate(orderDate);
        if (os == null){
            throw new EmptyException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //3. 判断预约数量是否已满
        if (os.getNumber() - os.getReservations() <= 0){
            throw new FullException(MessageConstant.ORDER_FULL);
        }
        //4. 判断用户是否重复预约（1个用户当天只能预约1个）
        Integer cnt = orderMapper.findCntByDate(orderDate,m.getId());
        if (cnt > 0){
            throw new ReplicateException(MessageConstant.HAS_ORDERED);
        }

        //4. 新增预约单
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setSetmealId(setmealId);
        order.setOrderdate(orderDate);
        order.setOrdertype(OrderTypeEnum.WECHAT.msg());
        order.setOrderstatus(OrderStatusEnum.PENDING.msg());
        orderMapper.insert(order);
        //5. 当天已预约数量+1
        os.setReservations(os.getReservations()+1);
        ordersettingMapper.updateByPrimaryKeySelective(os);

        //6. 返回预约订单号
        return order.getId();
    }
}