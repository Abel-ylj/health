package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
    * 管理角色表
    */
@ApiModel(value="cn-ylj-entity-Role")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="角色名称")
    private String name;

    @ApiModelProperty(value="关键字")
    private String keyword;

    @ApiModelProperty(value="描述")
    private String description;

    //角色拥有的权限列表
    private List<Permission> permissionList;

}