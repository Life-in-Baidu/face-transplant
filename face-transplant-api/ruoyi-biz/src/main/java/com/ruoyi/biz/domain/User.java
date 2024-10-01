package com.ruoyi.biz.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
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
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long id;

    /** 微信openid */
    @Excel(name = "微信openid")
    private String openid;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickname;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realname;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;
    private Integer phoneIsNull;

    private String password;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String avatar;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 出生年月 */
    @Excel(name = "出生年月")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** token */
    @Excel(name = "token")
    private String token;

    /** token过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "token过期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date tokenExpiredDate;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /** 登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /** 拓展信息字段 */
    @Excel(name = "拓展信息字段")
    private String extraInfo;

    @Excel(name = "小程序码")
    private String invitation;

    /** 用户身份（0-普通用户，1-经销商用户，2-管理员用户） */
    @Excel(name = "用户身份", readConverterExp = "0=-普通用户，1-经销商用户，2-管理员用户")
    private Integer identity;

    /** 普通积分 */
    @Excel(name = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private Long rewIntegral;
}
