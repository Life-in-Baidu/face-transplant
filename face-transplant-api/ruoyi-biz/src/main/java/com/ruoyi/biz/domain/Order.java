package com.ruoyi.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 支付记录对象 biz_order
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
@Data
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单号 */
    private Long orderId;

    /** 订单流水Id */
    @Excel(name = "订单流水Id")
    private String orderNo;

    /** 支付用户 */
    @Excel(name = "支付用户")
    private Long userId;

    /** 订单版本号 */
    @Excel(name = "订单版本号")
    private Integer version;

    /** 套餐Code */
    @Excel(name = "套餐Code")
    private Long mealId;

    /** 支付结果(0:待支付，1:已支付) */
    @Excel(name = "支付结果(0:待支付，1:已支付)")
    private Integer payResult;

    /** 支付方式(1:微信支付) */
    @Excel(name = "支付方式(1:微信支付)")
    private Integer payWay;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private Integer payMoney;

    /** 微信支付订单号 */
    @Excel(name = "微信支付订单号")
    private String transactionId;

    /** 访问入口 */
    @Excel(name = "访问入口")
    private String entry;

    /** 客户端 */
    @Excel(name = "客户端")
    private String client;

    /** 有效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date validDay;

}
