package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "注册信息对象")
public class RegisterParam {

    /** 手机号 */
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号格式有误")
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String code;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
