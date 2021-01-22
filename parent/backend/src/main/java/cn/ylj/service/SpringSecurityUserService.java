package cn.ylj.service;


import cn.ylj.entity.Permission;
import cn.ylj.entity.Role;
import cn.ylj.entity.User;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<Role> roleList = user.getRoleList();
        if (roleList != null && roleList.size() > 0){
            for (Role role : roleList) {
                //获取角色
                authorities.add(new SimpleGrantedAuthority(role.getKeyword()));
                List<Permission> permissionList = role.getPermissionList();
                if (permissionList != null && !permissionList.isEmpty()){
                    for (Permission permission : permissionList) {
                        //获取权限
                        authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        org.springframework.security.core.userdetails.User ssuser =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),//用户名
                        user.getPassword(),//用户数据库密码
                        authorities); //该用户的角色和权限
        return ssuser;//返回给框架，让框架来校验和授权，然后放到session
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String test = bc.encode("test");
        System.out.println(test);
    }
}