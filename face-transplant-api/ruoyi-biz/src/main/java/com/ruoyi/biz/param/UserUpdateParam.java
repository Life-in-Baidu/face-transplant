package com.ruoyi.biz.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户信息对象 biz_user
 *
 * @author zhouzhou
 * @date 2021-06-12
 */
@Data
@ApiModel(value = "用户信息更新对象")
public class UserUpdateParam {
    private static final long serialVersionUID = 1L;

    /** 用户昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 真实姓名 */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    /** 用户头像 */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private String sex;

    /** 出生年月 */
    @ApiModelProperty(value = "出生年月",example = "2021-10-18 19:23:25")
    private Date birthDate;

    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String address;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 拓展信息字段 */
    @ApiModelProperty(value = "拓展信息字段")
    private String extraInfo;

}
