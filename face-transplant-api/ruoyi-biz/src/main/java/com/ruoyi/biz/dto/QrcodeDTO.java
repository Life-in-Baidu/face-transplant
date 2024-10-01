package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzw
 * @date 2024/6/18
 */
@ApiModel(value = "分享积分对象")
@Data
public class QrcodeDTO {

    /**
     * 分享成功次数
     */
    @ApiModelProperty(value = "分享成功次数")
    private int number;

    /**
     * 分享总积分
     */
    @ApiModelProperty(value = "分享总积分")
    private Long sum;
}
