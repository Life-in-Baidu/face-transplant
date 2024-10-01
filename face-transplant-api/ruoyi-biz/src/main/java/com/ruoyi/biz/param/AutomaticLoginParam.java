package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自动登录参数
 */
@Data
public class AutomaticLoginParam {
    @ApiModelProperty(value = "手机号",required = true,example = "19176483846")
    private String phone;

    @ApiModelProperty(value = "version",example = "xiaomi12@android11",required = true)
    private String version;

    @ApiModelProperty(value = "入口",example = "默认入口portal/活动入口xxx等",required = true)
    private String entry;
    @ApiModelProperty(value = "客户端",example = "mini-program/h5/android/ios",required = true)
    private String client;
}
