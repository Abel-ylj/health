package cn.ylj.service;


import com.alibaba.dubbo.config.annotation.Reference;
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
        return null;
    }
}