package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.constant.RedisConstant;
import cn.ylj.model.Result;
import cn.ylj.utils.RadomCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author : yanglujian
 * create at:  2021/1/20  4:30 下午
 */
@RestController
@RequestMapping("/validate")
public class ValidateCodeController {

    @Resource
    private JedisPool jedisPool;

    @RequestMapping("/sendCode")
    public Result sendCode(@RequestParam("tel") String tel){
        Integer code = RadomCodeUtils.generateValidateCode(6);
        jedisPool.getResource().set(String.format(RedisConstant.SENDTYPE_ORDER, tel),code.toString());
        jedisPool.getResource().expire(String.format(RedisConstant.SENDTYPE_ORDER, tel), RedisConstant.EXPIRED_ORDER);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS,code);
    }
}