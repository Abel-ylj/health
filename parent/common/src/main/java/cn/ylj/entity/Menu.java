package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 管理菜单栏目表
    */
@ApiModel(value="cn-ylj-entity-Menu")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="菜单名称")
    private String name;

    @ApiModelProperty(value="菜单url")
    private String linkurl;

    @ApiModelProperty(value="路径")
    private String path;

    @ApiModelProperty(value="优先级")
    private Integer priority;

    @ApiModelProperty(value="icon")
    private String icon;

    @ApiModelProperty(value="描述")
    private String description;

    @ApiModelProperty(value="父菜单id")
    private Integer parentmenuid;

    @ApiModelProperty(value="菜单级别")
    private Integer level;



}