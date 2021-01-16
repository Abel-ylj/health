package cn.ylj.service.impl;

import cn.ylj.entity.Checkitem;
import cn.ylj.mapper.CheckitemMapper;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ICheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
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

    public PageResult pageQuery(QueryPageBean pb) {
        PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
        List<Checkitem> list = checkitemMapper.selectAll();
        PageInfo<Checkitem> pi = new PageInfo<Checkitem>(list);
        return new PageResult(pi.getTotal(), pi.getList());
    }
}