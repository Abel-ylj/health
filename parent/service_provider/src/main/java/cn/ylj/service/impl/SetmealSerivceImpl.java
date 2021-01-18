package cn.ylj.service.impl;

import cn.ylj.entity.Setmeal;
import cn.ylj.model.QueryPageBean;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:25 下午
 */
@Service(interfaceClass = ISetMealService.class)
public class SetmealSerivceImpl implements ISetMealService {
    public void add(Setmeal setmeal, Integer[] ids) {

    }

    public void delete(Integer id) {

    }

    public void update(Setmeal setmeal, Integer[] ids) {

    }

    public PageInfo findPage(QueryPageBean pb) {
        return null;
    }

    public Setmeal findOneById(Integer id) {
        return null;
    }
}