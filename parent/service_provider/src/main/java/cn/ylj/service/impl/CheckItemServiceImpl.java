package cn.ylj.service.impl;

import cn.ylj.entity.Checkitem;
import cn.ylj.mapper.CheckitemMapper;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
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

    public void deleteById(Integer id) {
        checkitemMapper.deleteByPrimaryKey(id);
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
}