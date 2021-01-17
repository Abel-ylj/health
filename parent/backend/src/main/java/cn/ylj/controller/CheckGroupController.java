package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkgroup;
import cn.ylj.model.Result;
import cn.ylj.service.ICheckgroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yanglujian
 * create at:  2021/1/17  8:47 上午
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    ICheckgroupService checkgroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody Checkgroup checkgroup, Integer[] checkitemIds){
        try {
            int r = checkgroupService.add(checkgroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS, r);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }
}