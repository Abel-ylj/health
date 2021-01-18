package cn.ylj.service.impl;

import cn.ylj.entity.Setmeal;
import cn.ylj.mapper.SetmealMapper;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:25 下午
 */
@Service(interfaceClass = ISetMealService.class)
public class SetmealSerivceImpl implements ISetMealService {

    @Resource
    SetmealMapper setmealMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void add(Setmeal setmeal, Integer[] ids) {
        setmealMapper.insert(setmeal);
        //插入关系
        setmealMapper.insertRel(setmeal.getId(), ids);
    }

    public void delete(Integer id) {

    }

    public void update(Setmeal setmeal, Integer[] ids) {

    }

    public Page<Setmeal> findPage(QueryPageBean pb) {
        PageHelper.startPage(pb.getCurrentPage(), pb.getPageSize());
        Page<Setmeal> page = setmealMapper.findPage(pb.getQueryString());
        return page;
    }

    public Setmeal findOneById(Integer id) {
        return null;
    }
}