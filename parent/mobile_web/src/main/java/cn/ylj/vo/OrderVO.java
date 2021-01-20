package cn.ylj.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author : yanglujian
 * create at:  2021/1/20  5:03 下午
 */
@Data
public class OrderVO {

    private String name;

    private String sex;

    private String phoneNumber;

    private String code;

    private String idCard;

    private Date orderDate;

    private Integer setmealId;
}