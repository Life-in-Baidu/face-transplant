package com.ruoyi.pay.param;

import lombok.Data;

/**
 * 交易参数实体类
 */
@Data
public class TransfersParam {
    /** 用户openid */
    private String openid;
    /**
     * 校验用户姓名选项
     * NO_CHECK：不校验真实姓名, FORCE_CHECK：强校验真实姓名
     */
    private String checkName;
    /**
     * 收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     */
    private String reUserName;
    /** 付款金额，单位为分(不能低于1元) */
    private Integer amount;
    /** 付款备注，必填。注意：备注中的敏感词会被转成字符* */
    private String desc;
}
