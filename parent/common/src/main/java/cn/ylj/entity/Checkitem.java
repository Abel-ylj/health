package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
    * 检查项表
    */
@ApiModel(value="cn-ylj-entity-Checkitem")
@Data
public class Checkitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="检查项编码")
    private String code;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="项目适用性别")
    private String sex;

    @ApiModelProperty(value="适用年龄")
    private String age;

    @ApiModelProperty(value="项目价格")
    private Double price;

    @ApiModelProperty(value="查检项类型,分为检查和检验两种")
    private String type;

    @ApiModelProperty(value="注意事项")
    private String attention;

    @ApiModelProperty(value="备注")
    private String remark;

}