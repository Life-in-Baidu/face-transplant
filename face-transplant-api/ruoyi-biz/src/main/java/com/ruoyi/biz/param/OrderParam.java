package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("订单参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderParam {

    /** 支付用户 */
    @ApiModelProperty(value = "支付用户")
    private Long userId;

    /** 套餐Code */
    @ApiModelProperty(value  = "套餐id")
    private Long mealId;

    /** 支付方式(1:微信支付) */
    @ApiModelProperty(value  = "支付方式(1:微信支付)")
    private Integer payWay;

    /** 支付金额 */
    @ApiModelProperty(value  = "支付金额")
    private Integer payMoney;

    /**
     * 通用日志
     */

    @ApiModelProperty(value = "入口",example = "默认入口portal/活动入口xxx等")
    private String entry;

    @ApiModelProperty(value = "客户端",example = "mini-program/h5/android/ios")
    private String client;

}
