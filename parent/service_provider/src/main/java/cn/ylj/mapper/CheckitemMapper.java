package cn.ylj.mapper;

import cn.ylj.entity.Checkitem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckitemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Checkitem record);

    int insertSelective(Checkitem record);

    Checkitem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Checkitem record);

    int updateByPrimaryKey(Checkitem record);

    List<Checkitem> selectAll();

    Page<Checkitem> findPageByCondition(@Param("condition") String condition);

    Integer selectCheckgroupCntsByItemId(@Param("id") Integer id);
}