package cn.ylj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
    * 管理菜单栏目表
    */
@ApiModel(value="cn-ylj-entity-Menu")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(serialize = false)
    @ApiModelProperty(value="")
    private Integer id;

    @JSONField(name = "title")
    @ApiModelProperty(value="菜单名称")
    private String name;

    @JSONField(name = "linkUrl")
    @ApiModelProperty(value="菜单url")
    private String linkurl;

    @JSONField(name = "path")
    @ApiModelProperty(value="路径")
    private String path;

    @JSONField(serialize = false)
    @ApiModelProperty(value="优先级")
    private Integer priority;

    @JSONField(name = "icon")
    @ApiModelProperty(value="icon")
    private String icon;

    @JSONField(serialize = false)
    @ApiModelProperty(value="描述")
    private String description;

    @JSONField(serialize = false)
    @ApiModelProperty(value="父菜单id")
    private Integer parentmenuid;

    @JSONField(serialize = false)
    @ApiModelProperty(value="菜单级别")
    private Integer level;

    @JSONField(name = "children")
    private List<Menu> children = new ArrayList<>();//子菜单集合

    @JSONField(name = "valid")
    private boolean valid = false;
}