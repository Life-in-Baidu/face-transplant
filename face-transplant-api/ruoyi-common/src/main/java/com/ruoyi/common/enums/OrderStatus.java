package com.ruoyi.common.enums;

import cn.hutool.extra.expression.ExpressionException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    /**
     * 待支付
     */
    RECEIVING(0),

    /**
     * 已支付
     */
    PAID(1);
    final int value;

    OrderStatus(int value) {
        this.value = value;
    }


    public static OrderStatus isIllegal(int value) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.getValue() == value)
                .findAny().orElseThrow(() -> new ExpressionException("订单状态"));
    }

    public static OrderStatus getByValue(int val) {
        return Arrays.stream(OrderStatus.values()).filter(orderStatus -> orderStatus.getValue() == val).findFirst()
                .orElse(null);
    }
}
