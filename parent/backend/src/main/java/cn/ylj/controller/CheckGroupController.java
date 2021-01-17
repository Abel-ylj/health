package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkgroup;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.model.Result;
import cn.ylj.service.ICheckgroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        try {
            PageInfo<Checkgroup> page = checkgroupService.findPage(pageBean);
            return new PageResult(page.getTotal(), page.getList());
        } catch (Exception e){
            return null;
        }
    }
}