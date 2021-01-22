package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Checkitem;
import cn.ylj.model.PageResult;
import cn.ylj.model.QueryPageBean;
import cn.ylj.model.Result;
import cn.ylj.service.ICheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yanglujian
 * create at:  2021/1/15  10:19 下午
 */
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    @Reference
    private ICheckItemService checkItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody Checkitem checkitem){
        try{
            checkItemService.add(checkitem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/{itemId}/delete")
    public Result delete(@PathVariable("itemId")Integer itemId){
        try{
            return checkItemService.deleteById(itemId);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/update")
    public Result update(@RequestBody Checkitem checkitem){
        try{
            return checkItemService.updateById(checkitem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("itemId")Integer itemId){
        try{
            return checkItemService.findById(itemId);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        return checkItemService.pageQuery(queryPageBean);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        return checkItemService.findAll();
    }
}