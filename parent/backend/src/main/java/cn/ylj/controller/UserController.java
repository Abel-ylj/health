package cn.ylj.controller;

import cn.ylj.constant.MessageConstant;
import cn.ylj.model.Result;
import cn.ylj.service.IUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author : yanglujian
 * create at:  2021/1/22  11:50 上午
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Reference
    private IUserService userService;

    @RequestMapping("/info")
    public Result getUserInfo(HttpServletRequest req){
        //获取用户id，因为是被认证过才能到这里，直接从session中获取即可
        HashMap<String, String> map = new HashMap<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null){
            map.put("username",user.getUsername());
            //应该从数据库中查找,随便写写
            map.put("avatar","https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-22-avatar.png");
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,map);
        } else {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}