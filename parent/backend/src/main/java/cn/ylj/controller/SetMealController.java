package cn.ylj.controller;

import cn.ylj.entity.Setmeal;
import cn.ylj.model.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yanglujian
 * create at:  2021/1/18  1:20 下午
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] ids){
        return null;
    }
}