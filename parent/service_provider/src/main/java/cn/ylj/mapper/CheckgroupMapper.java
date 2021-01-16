package cn.ylj.mapper;

import cn.ylj.entity.Checkgroup;

public interface CheckgroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Checkgroup record);

    int insertSelective(Checkgroup record);

    Checkgroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Checkgroup record);

    int updateByPrimaryKey(Checkgroup record);
}