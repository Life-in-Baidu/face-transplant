package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName : WeChatMiniProgramLoginParam
 * @Description : 微信小程序登录入参
 * @Author : Fly
 * @Date: 2021-01-26 15:17
 */
@ApiModel(value = "小程序登录参数")
@Data
public class LoginParam {

    @ApiModelProperty(value = "微信code",required = true,example = "xxx")
    private String code;

    @ApiModelProperty(value = "加密数据，用户敏感信息",example = "xxx")
    private String encryptedData;

    @ApiModelProperty(value = "加密初始化向量",example = "xxx")
    private String iv;

    @ApiModelProperty(value = "uuid")
    private String uuid;

}