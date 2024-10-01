package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动对象 biz_activity
 *
 * @author shijieming
 * @date 2021-02-26
 */
@ApiModel(value = "字典数据")
@Data
public class DictDataDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 标签 */
    @ApiModelProperty(value = "标签")
    private String dictLabel;


    @ApiModelProperty(value = "字典code(需要的数据字典)")
    private Integer dictCode;

}
