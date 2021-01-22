package cn.ylj.mapper;

import cn.ylj.entity.Menu;
import cn.ylj.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    Set<Menu> findMenusByRoles(@Param("roles") List<Role> roles);

    List<Menu> findAll();
}