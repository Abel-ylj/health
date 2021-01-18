package cn.ylj.mapper;

import cn.ylj.entity.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface SetmealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);

    void insertRel(@Param("id") Integer id, @Param("ids") Integer[] ids);

    Page<Setmeal> findPage(@Param("condition") String condition);

    Integer[] findRelBySetmealId(@Param("id") Integer id);
}