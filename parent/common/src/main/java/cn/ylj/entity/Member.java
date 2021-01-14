package cn.ylj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 会员表
    */
@ApiModel(value="cn-ylj-entity-Member")
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="")
    private Integer id;

    @ApiModelProperty(value="会员名称")
    private String name;

    @ApiModelProperty(value="文件数量")
    private String filenumber;

    @ApiModelProperty(value="性别")
    private String sex;

    @ApiModelProperty(value="身份证信息")
    private String idcard;

    @ApiModelProperty(value="电话号码")
    private String phonenumber;

    @ApiModelProperty(value="注册时间")
    private Date regtime;

    @ApiModelProperty(value="密码")
    private String password;

    @ApiModelProperty(value="电子邮件")
    private String email;

    @ApiModelProperty(value="生日")
    private Date birthday;

    @ApiModelProperty(value="备注")
    private String remark;
}