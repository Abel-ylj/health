package cn.ylj.service;

import cn.ylj.entity.Setmeal;
import cn.ylj.model.QueryPageBean;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:23 下午
 */
public interface ISetMealService {

    void add(Setmeal setmeal, Integer[] ids);

    void delete(Integer id);

    void update(Setmeal setmeal, Integer[] ids);

    Page<Setmeal> findPage(QueryPageBean pb);

    Setmeal findOneById(Integer id);

    List<Integer> findRelBySetmealId(Integer id);

    /**
     * 移动H5 获取套餐列表
     * @return
     */
    List<Setmeal> getSetmealList();
}