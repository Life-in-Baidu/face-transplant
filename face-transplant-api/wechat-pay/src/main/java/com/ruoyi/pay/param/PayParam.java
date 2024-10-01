package com.ruoyi.pay.param;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayParam {

    /**
     * 用户Openid
     */
    private String openId;

    /**
     * 订单原价格
     */
    private Integer price;

    /**
     * 订单描述信息
     */
    private String description;

    /**
     * 商户订单号
     */
    private String orderNo;

}
