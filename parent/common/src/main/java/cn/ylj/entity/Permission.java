package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 管理权限表
    */
@ApiModel(value="cn-ylj-entity-Permission")
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="权限名称")
    private String name;

    @ApiModelProperty(value="关键字")
    private String keyword;

    @ApiModelProperty(value="描述")
    private String description;

}