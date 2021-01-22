package cn.ylj.mapper;

import cn.ylj.entity.Permission;
import cn.ylj.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    //根据角色Id获取隶属的所有权限
    List<Permission> findPermissionsByRid(@Param("rId") Integer rId);

    List<Role> findRolesByUsername(@Param("username") String username);
}