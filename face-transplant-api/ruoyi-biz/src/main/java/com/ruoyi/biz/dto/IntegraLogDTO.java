package com.ruoyi.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.biz.domain.IntegralLog;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hzw
 * @date 2024/8/10
 */
@Data
@ApiModel(value = "积分日志")
public class IntegraLogDTO {


    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 支付金额 */
    @ApiModelProperty(value = "支付金额")
    private Integer payMoney;

    /** 普通积分 */
    @ApiModelProperty(value = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private Long rewIntegral;

    /** 所属用户 */
    @ApiModelProperty(value = "所属用户")
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public static IntegraLogDTO convert(IntegralLog integralLog) {
        IntegraLogDTO integraLogDTO = new IntegraLogDTO();
        integraLogDTO.setId(integralLog.getId());
        integraLogDTO.setPayMoney(integralLog.getPayMoney());
        integraLogDTO.setOrdIntegral(integralLog.getOrdIntegral());
        integraLogDTO.setRewIntegral(integralLog.getRewIntegral());
        integraLogDTO.setUserId(integralLog.getUserId());
        integraLogDTO.setCreateTime(integralLog.getCreateTime());
        integraLogDTO.setUpdateTime(integralLog.getUpdateTime());
        return integraLogDTO;
    }
}
