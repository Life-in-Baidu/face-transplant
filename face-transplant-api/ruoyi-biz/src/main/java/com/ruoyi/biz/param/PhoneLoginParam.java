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
@ApiModel(value = "手机登录参数")
@Data
public class PhoneLoginParam {

    /**
     * 微信code，后端用于获取secretKey
     */
    @ApiModelProperty(value = "验证码",required = true,example = "xxx")
    private String code;

/*    @ApiModelProperty(value = "邀请人id",example = "xxx")
    private Long inviter;*/

    @ApiModelProperty(value = "手机号",required = true,example = "13700001111")
    private String phone;

    @ApiModelProperty(value = "入口",example = "默认入口portal/活动入口xxx等")
    private String entry;

    @ApiModelProperty(value = "客户端",example = "mini-program/h5/android/ios")
    private String client;

}
