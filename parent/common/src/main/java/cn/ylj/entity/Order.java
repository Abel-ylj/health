package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 预约订单表
    */
@ApiModel(value="cn-ylj-entity-Order")
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="员会id")
    private Integer memberId;

    @ApiModelProperty(value="约预日期")
    private Date orderdate;

    @ApiModelProperty(value="约预类型 电话预约/微信预约")
    private String ordertype;

    @ApiModelProperty(value="预约状态（是否到诊）")
    private String orderstatus;

    @ApiModelProperty(value="餐套id")
    private Integer setmealId;

}