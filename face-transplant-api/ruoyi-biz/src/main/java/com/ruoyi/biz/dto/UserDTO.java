package com.ruoyi.biz.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息对象 biz_user
 * 
 * @author zhouzhou
 * @date 2021-06-12
 */
@Data
@ApiModel(value = "用户信息对象")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long id;

    /** 微信openid */
    @ApiModelProperty(value = "微信openid")
    private String openid;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 真实姓名 */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 用户头像 */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月")
    private Date birthDate;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String address;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** token */
    @ApiModelProperty(value = "token")
    private String token;


    /** 拓展信息字段 */
    @ApiModelProperty(value = "拓展信息字段")
    private String extraInfo;

//    @ApiModelProperty(value = "当前用户有效次数")
//    private Integer validNum;

//    @ApiModelProperty(value = "有效时间")
//    private Date validTime;

    /** 普通积分 */
    @ApiModelProperty(name = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @ApiModelProperty(name = "奖励积分")
    private Long rewIntegral;

    /**
     * 随机图片
     */
    @ApiModelProperty(value = "随机图片")
    private String url;

}
