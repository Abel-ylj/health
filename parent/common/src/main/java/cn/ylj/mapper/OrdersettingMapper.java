package cn.ylj.mapper;

import cn.ylj.entity.Ordersetting;

public interface OrdersettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ordersetting record);

    int insertSelective(Ordersetting record);

    Ordersetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ordersetting record);

    int updateByPrimaryKey(Ordersetting record);
}