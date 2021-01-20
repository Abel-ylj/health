package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Member;
import cn.ylj.entity.Order;
import cn.ylj.entity.Setmeal;
import cn.ylj.model.Result;
import cn.ylj.service.IMemberService;
import cn.ylj.service.IOrderService;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.geom.AreaOp;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 用户预约信息Order
 * @author : yanglujian
 * create at:  2021/1/20  10:07 下午
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    IOrderService orderService;
    @Reference
    IMemberService memberService;
    @Reference
    ISetMealService setMealService;

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            Order order = orderService.findById(id);
            if (order != null){
                Member member = memberService.findById(order.getMemberId());
                Setmeal setmeal = setMealService.findOneById(order.getSetmealId());
                map.put("member", member.getName());
                map.put("setmeal",setmeal.getName());
                map.put("orderDate", order.getOrderdate());
                map.put("orderType", order.getOrdertype());
            }
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}