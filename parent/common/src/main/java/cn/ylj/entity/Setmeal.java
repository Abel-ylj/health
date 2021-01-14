package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
    * 检查套餐表
    */
@ApiModel(value="cn-ylj-entity-Setmeal")
@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="")
    private String name;

    @ApiModelProperty(value="")
    private String code;

    @ApiModelProperty(value="")
    private String helpcode;

    @ApiModelProperty(value="")
    private String sex;

    @ApiModelProperty(value="")
    private String age;

    @ApiModelProperty(value="")
    private Double price;

    @ApiModelProperty(value="")
    private String remark;

    @ApiModelProperty(value="")
    private String attention;

    @ApiModelProperty(value="")
    private String img;

    private List<Setmeal> setmealList;

}