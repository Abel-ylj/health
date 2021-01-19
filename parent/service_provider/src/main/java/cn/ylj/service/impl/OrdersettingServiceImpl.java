package cn.ylj.service.impl;

import cn.ylj.mapper.OrdersettingMapper;
import cn.ylj.service.IOrdersettingService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : yanglujian
 * create at:  2021/1/19  7:22 下午
 */
//为了防止tx代理后，发布应用失败
@Service(interfaceClass = IOrdersettingService.class)
public class OrdersettingServiceImpl implements IOrdersettingService {

    @Resource
    private OrdersettingMapper ordersettingMapper;

    @Override
    public void importOrderSetting(List<String[]> list) {
        List<Map> convert = convert(list);
        ordersettingMapper.insertList(convert);
    }

    private List<Map> convert(List<String[]> lst) {
        return lst.stream().map(o -> {
            Map<String, String> map = new HashMap<>();
            map.put("odate", o[0]);
            map.put("num", o[1]);
            return map;
        }).collect(Collectors.toList());
    }

}