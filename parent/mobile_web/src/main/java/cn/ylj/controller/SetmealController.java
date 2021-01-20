package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Setmeal;
import cn.ylj.model.Result;
import cn.ylj.service.ISetMealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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