package com.ruoyi.biz.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.biz.domain.Order;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    /** 订单号 */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /** 订单流水Id */
    @ApiModelProperty(value = "订单流水Id")
    private String orderNo;

    /** 支付用户 */
    @ApiModelProperty(value = "支付用户")
    private Long userId;

    /** 订单版本号 */
    @ApiModelProperty(value = "订单版本号")
    private Integer version;

    /** 套餐Code */
    @ApiModelProperty(value = "套餐Code")
    private Long mealId;

    /** 支付结果(0:待支付，1:已支付) */
    @ApiModelProperty(value = "支付结果(0:待支付，1:已支付)")
    private Integer payResult;

    /** 支付方式(1:微信支付) */
    @ApiModelProperty(value = "支付方式(1:微信支付)")
    private Integer payWay;

    /** 支付金额 */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payMoney;

    /** 微信支付订单号 */
    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;

    @ApiModelProperty(value = "支付签名参数")
    private JSONObject signParams;

    /** 创建时间 */
    @ApiModelProperty(value = "支付时间")
    private Date createTime;

    public static OrderDTO convert(Order order){
        if(Objects.isNull(order)) {
            return null;
        }
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderNo(order.getOrderNo())
                .userId(order.getUserId())
                .version(order.getVersion())
                .mealId(order.getMealId())
                .payResult(order.getPayResult())
                .payWay(order.getPayWay())
//                .payMoney(BigDecimalUtil.formatMoney(order.getPayMoney()))
                .transactionId(order.getTransactionId())
                .build();
    }

}
