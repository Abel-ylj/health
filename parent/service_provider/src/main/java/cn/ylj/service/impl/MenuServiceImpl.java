package cn.ylj.service.impl;
import cn.ylj.mapper.RoleMapper;
import cn.ylj.mapper.UserMapper;
import com.google.common.collect.Lists;

import cn.ylj.entity.Menu;
import cn.ylj.entity.Role;
import cn.ylj.mapper.MenuMapper;
import cn.ylj.service.IMenuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : yanglujian
 * create at:  2021/1/22  1:52 下午
 */
@Service(interfaceClass = IMenuService.class)
//    @Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    MenuMapper menuMapper;
    @Resource
    RoleMapper roleMapper;

    /**
     * 根据username获取Role，根据role获取响应的menu
     * @param username
     * @return
     */
    @Override
    public List<Menu> findMenusByUsername(String username) {
        List<Role> roles = roleMapper.findRolesByUsername(username);
        Set<Menu> menus = this.findMenusByRoles(roles);
        List<Menu> all = this.findAll();
        //将需要显示的menu置位
        Map<Integer, Menu> mapEnable = new HashMap<>();
        for (Menu menu : menus) {
            mapEnable.put(menu.getId(), menu);
        }
        for (Menu menu : all) {
            if (mapEnable.containsKey(menu.getId())){
                menu.setValid(true);
            }
        }
        //构建目录树
        List<Menu> r = this.buildMenuArch(all);
        return r;
    }

    /**
     * 角色对应需要显示的menu
     * @param roles
     * @return
     */
    private Set<Menu> findMenusByRoles(List<Role> roles){
        return menuMapper.findMenusByRoles(roles);
    }

    /**
     * 所有菜单
     * @return
     */
    private List<Menu> findAll(){
        return menuMapper.findAll();
    }

    //遍历1遍List构建树结构
    //1. 查看map中是否已有记录，有则先获取下来，同步后在将数据覆盖回去
    //2. 若当前节点有parentId存在，就挂到map中相应的节点下，没有先创建空的父节点
    private List<Menu> buildMenuArch(List<Menu> all){
        ArrayList<Menu> rlst = new ArrayList<>();
        HashMap<Integer, Menu> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(all)){
            Menu m, pm;
            Integer parentId;
            for (Menu menu : all) {
                //同步信息
                m = map.get(menu.getId());
                if (m != null){
                    menu.setChildren(m.getChildren());
                }
                map.put(menu.getId(), menu);
                //判断parentId存在，将自己挂上去
                parentId = menu.getParentmenuid();
                if (parentId != null && parentId > 0){
                    //父menu不在map中，先创建
                    map.computeIfAbsent(parentId, k -> new Menu());
                    pm = map.get(parentId);
                    pm.getChildren().add(menu);
                } else {
                    //没有parent表示是1级菜单
                    rlst.add(menu);
                }
            }
        }
        return rlst;
    }


}