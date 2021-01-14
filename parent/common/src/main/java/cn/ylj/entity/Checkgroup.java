package cn.ylj.entity;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
    * 检查组表
    */
@ApiModel(value="cn-ylj-entity-Checkgroup")
@Data
public class Checkgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="检查组编码")
    private String code;

    @ApiModelProperty(value="检查组名称")
    private String name;

    @ApiModelProperty(value="助记码")
    private String helpcode;

    @ApiModelProperty(value="适配性别")
    private String sex;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="注意事项")
    private String attention;

    private List<Checkitem> checkitemList;
}