package cn.ylj.service.impl;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkitem;
import cn.ylj.mapper.CheckitemMapper;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.model.Result;
import cn.ylj.service.ICheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/15  10:29 下午
 */
@Service
public class CheckItemServiceImpl implements ICheckItemService {

    @Resource
    private CheckitemMapper checkitemMapper;

    public void add(Checkitem checkitem) {
        checkitemMapper.insert(checkitem);
    }

    public Result deleteById(Integer id) {
        //1. 查询被删除项是否被其他模块依赖(由业务可知，checkitem会被checkgroup检查组依赖)
        Integer cnt = checkitemMapper.selectCheckgroupCntsByItemId(id);
        //2. 若已经被依赖就提示删除失败
        if (cnt > 0){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_MSG);
        } else {
            //3. 未被依赖就执行删除
            checkitemMapper.deleteByPrimaryKey(id);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    public PageResult pageQuery(QueryPageBean pb) {
        PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
        //1. 方式一： 手动分装Pagehelper组件的Page对象(原理就是从threadlocal中去拿total，currentPage，pageSize值)
//        List<Checkitem> list = checkitemMapper.selectAll();
//        PageInfo<Checkitem> pi = new PageInfo<Checkitem>(list);
//        return new PageResult(pi.getTotal(), pi.getList());
        //2. 方式二: 自动封装
        Page<Checkitem> pg = checkitemMapper.findPageByCondition(pb.getQueryString());
        return new PageResult(pg.getTotal(), pg.getResult());
    }

    public Result findById(Integer itemId) {
        Checkitem checkitem = checkitemMapper.selectByPrimaryKey(itemId);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkitem);
    }
}