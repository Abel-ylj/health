package cn.ylj.service;

import cn.ylj.entity.Menu;

import java.util.List;

public interface IMenuService {

    List<Menu> findMenusByUsername(String username);
}
