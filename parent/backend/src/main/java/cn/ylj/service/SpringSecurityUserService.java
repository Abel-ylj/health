package cn.ylj.service;


import cn.ylj.entity.User;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : yanglujian
 * create at:  2021/1/21  8:03 下午
 */
@Service("springSecurityUserService")
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if (user == null)
            return null;

        //获取数据库中username指向用户的角色和权限


        org.springframework.security.core.userdetails.User ssuser =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),//用户名
                        user.getPassword(),//用户数据库密码
                        Lists.newArrayList()); //该用户的权限
        return ssuser;//返回给框架，让框架来校验和授权，然后放到session
    }
}