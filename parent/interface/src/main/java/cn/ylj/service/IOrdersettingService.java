package cn.ylj.service;

import cn.ylj.entity.Ordersetting;

import java.util.Date;
import java.util.List;

public interface IOrdersettingService {
    void importOrderSetting(List<Ordersetting> list);

    List<Ordersetting> getOrdersettingByMonth(Date date);
}
