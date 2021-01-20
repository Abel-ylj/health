package cn.ylj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 预约设置表
    */
@ApiModel(value="cn-ylj-entity-Ordersetting")
@Data
public class Ordersetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="约预日期")
    @JSONField(name="date",format = "dd")
    private Date orderdate;

    @ApiModelProperty(value="可预约人数")
    private Integer number;

    @ApiModelProperty(value="已预约人数")
    private Integer reservations;

}