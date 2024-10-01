package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信跳转通用参数
 */
@ApiModel("微信跳转通用参数")
@Data
@Builder
public class WxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "nonceStr")
    private String noncestr;

    @ApiModelProperty(value = "timestamp")
    private String timestamp;

    @ApiModelProperty(value = "signature")
    private String signature;
}
