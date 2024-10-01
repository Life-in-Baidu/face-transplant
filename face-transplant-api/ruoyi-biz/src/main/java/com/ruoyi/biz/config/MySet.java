package com.ruoyi.biz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hzw
 * @date 2024/6/13
 */
@Component
public class MySet {


    /**
     * 新用户普通积分
     */
    public static Long ordIntegral;

    /**
     * 分享奖励积分
     */
    public static Long rewIntegral;


    /**
     * 普通积分(元/积分)
     */
    public static Long payValidNum;



    @Value("${mySet.ordIntegral}")
    public void setOrdIntegral(Long ordIntegral) {
        MySet.ordIntegral = ordIntegral;
    }

    @Value("${mySet.rewIntegral}")
    public void getOrdIntegral(Long rewIntegral) {
        MySet.rewIntegral = rewIntegral;
    }

    @Value("${mySet.pay.validNum}")
    public void setPayValidNum(Long payValidNum) {
        MySet.payValidNum = payValidNum;
    }
}
