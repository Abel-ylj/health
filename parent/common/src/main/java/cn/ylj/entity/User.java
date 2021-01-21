package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
    * 管理员表
    */
@ApiModel(value="cn-ylj-entity-User")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private Date birthday;

    @ApiModelProperty(value="")
    private String gender;

    @ApiModelProperty(value="")
    private String username;

    @ApiModelProperty(value="")
    private String password;

    @ApiModelProperty(value="")
    private String remark;

    @ApiModelProperty(value="")
    private String station;

    @ApiModelProperty(value="")
    private String telephone;

    //用户可见的菜单列表
    private List<Menu> menuList;
    //用户拥有的角色列表
    private List<Role> roleList;

}