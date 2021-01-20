package cn.ylj.service.impl;

import cn.ylj.entity.Order;
import cn.ylj.mapper.OrderMapper;
import cn.ylj.service.IOrderService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/20  10:12 下午
 */

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    OrderMapper orderMapper;

    @Override
    public Order findById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }
}