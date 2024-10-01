package com.ruoyi.biz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积分变动日志对象 biz_integral_log
 * 
 * @author hzw
 * @date 2024-08-10
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IntegralLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private Integer payMoney;

    /** 普通积分 */
    @Excel(name = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private Long rewIntegral;

    /** 所属用户 */
    @Excel(name = "所属用户")
    private Long userId;

}
