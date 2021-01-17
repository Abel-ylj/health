package cn.ylj.mapper;

import cn.ylj.entity.Checkgroup;
import org.apache.ibatis.annotations.Param;

public interface CheckgroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Checkgroup record);

    int insertSelective(Checkgroup record);

    Checkgroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Checkgroup record);

    int updateByPrimaryKey(Checkgroup record);

    /**
     * 插入关系表， checkItem 1: checkGroup n
     *
     * @param id
     * @param checkitemIds
     */
    void insertRel(@Param("id") Integer id, @Param("checkitemIds") Integer[] checkitemIds);
}