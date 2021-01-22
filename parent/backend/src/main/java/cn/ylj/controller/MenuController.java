package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.entity.Menu;
import cn.ylj.model.Result;
import cn.ylj.service.IMenuService;
import cn.ylj.service.SpringSecurityUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单模块
 *
 * @author : yanglujian
 * create at:  2021/1/22  1:47 下午
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private IMenuService menuService;

    /**
     * 根据已认证用户的角色 展示菜单
     *
     * @return
     */
    @RequestMapping("/list")
    public Result getMenuList() {
        //获取已认证用户用户名
        try {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Menu> lst = menuService.findMenusByUsername(u.getUsername());
            return new Result(true, MessageConstant.GET_MENU_SUCCESS, lst);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }
}