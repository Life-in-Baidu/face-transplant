package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hzw
 * @date 2024/6/11
 */
@Data
@ApiModel("分享参数信息")
public class QrcodeParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    /** 备选相册id */
    @ApiModelProperty(value = "备选相册id")
    private Long mergeListId;

}
