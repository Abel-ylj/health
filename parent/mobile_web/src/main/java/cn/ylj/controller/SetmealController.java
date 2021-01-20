package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.constant.RedisConstant;
import cn.ylj.entity.Member;
import cn.ylj.entity.Setmeal;
import cn.ylj.ex.EmptyException;
import cn.ylj.ex.FullException;
import cn.ylj.ex.ReplicateException;
import cn.ylj.model.Result;
import cn.ylj.service.ISetMealService;
import cn.ylj.vo.OrderVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yanglujian
 * create at:  2021/1/20  3:01 下午
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private ISetMealService setMealService;
    @Resource
    private JedisPool jedisPool;

    @RequestMapping("/order")
    public Result order(@RequestBody OrderVO vo){
        //1. 验证码校验
        String codeInRedis = jedisPool.getResource().get(String.format(RedisConstant.SENDTYPE_ORDER, vo.getPhoneNumber()));
        if (codeInRedis == null || !codeInRedis.equals(vo.getCode())){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //2. 预约核心业务逻辑
        try {
            Member member = new Member();
            member.setName(vo.getName());
            member.setSex(vo.getSex());
            member.setPhonenumber(vo.getPhoneNumber());
            member.setIdcard(vo.getIdCard());
            Integer id = setMealService.order(member,vo.getSetmealId(),vo.getOrderDate());
            return new Result(true, MessageConstant.ORDER_SUCCESS, id);
        }
        catch (ReplicateException rex){
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        catch (FullException fex){
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        catch (EmptyException ee){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/getSetmealList")
    public Result getSetmealList(){
        try {
            List<Setmeal> setmeals = setMealService.getSetmealList();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeals);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }

    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try{
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        }catch (Exception e){
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}