package cn.ylj.service.impl;

import cn.ylj.entity.User;
import cn.ylj.mapper.UserMapper;
import cn.ylj.service.IUserService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * 后台管理员 服务
 * @author : yanglujian
 * create at:  2021/1/21  8:17 下午
 */
@Service(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        User user = userMapper.findOneByUsername(name);
        return user;
    }
}