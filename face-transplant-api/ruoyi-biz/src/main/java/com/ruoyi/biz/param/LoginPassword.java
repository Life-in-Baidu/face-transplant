package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "手机密码登录参数")
@Data
public class LoginPassword {
    @ApiModelProperty(value = "手机号",required = true,example = "19176483846")
    private String phone;
    @ApiModelProperty(value = "密码",required = true,example = "575777jk")
    private String password;

    @ApiModelProperty(value = "入口",example = "默认入口portal/活动入口xxx等")
    private String entry;

    @ApiModelProperty(value = "客户端",example = "mini-program/h5/android/ios")
    private String client;
}
