package com.ruoyi.biz.domain;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分享记录对象 biz_qrcode
 * 
 * @author hzw
 * @date 2024-06-18
 */
@Data
@Builder
public class Qrcode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分享id */
    private Long id;

    /** 分享者id */
    @Excel(name = "分享者id")
    private Long userId;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private Long rewIntegral;

}
