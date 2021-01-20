package cn.ylj.mapper;

import cn.ylj.entity.Ordersetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrdersettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ordersetting record);

    int insertSelective(Ordersetting record);

    Ordersetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ordersetting record);

    int updateByPrimaryKey(Ordersetting record);

    void insertList(@Param("list") List<Map> list);

    void insertOrdersettingList(@Param("list") List<Ordersetting> list);

    List<Ordersetting> getOrdersettingByMonth(Date date);
}