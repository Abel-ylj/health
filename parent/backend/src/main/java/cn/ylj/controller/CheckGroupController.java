package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkgroup;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.model.Result;
import cn.ylj.service.ICheckgroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            checkgroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
            //TODO 查看这个异常的捕获问题
        } catch (RuntimeException ex){
          return new Result(false, ex.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Checkgroup checkgroup,@RequestParam("ids") Integer[] ids){
        try{
            checkgroupService.update(checkgroup,ids);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e){
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
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

    /**
     * 查询id 指定的检查组(包含关联的检查项目)
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try{
            Checkgroup checkgroup = checkgroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroup);
        } catch (Exception e){
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}