package com.ruoyi.biz.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hzw
 * @date 2024/5/5
 */
@ApiModel(value = "目录对象")
@Data
public class PicDirectoryListDTO {

    /** 目录id */
    private Long id;

    /** 上级目录id */
    @ApiModelProperty(value = "上级目录id")
    private Long supDirectoryId;

    /** 目录图片url */
    @ApiModelProperty(value = "目录图片url")
    private String directoryUrl;

    /** 目录名称 */
    @ApiModelProperty(value = "目录名称")
    private String directoryName;

    /** 价格 */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private BigDecimal priceRew;

    /** 基础图片数量 */
    @ApiModelProperty(value = "基础图片数量")
    private Long numberFoun;

    /** 奖励图片数量 */
    @ApiModelProperty(value = "奖励图片数量")
    private Long numberRew;

    /** 使用次数 */
    @ApiModelProperty(value = "使用次数")
    private Long usageTimes;

    /** 目录介绍（主要是三级目录） */
    @ApiModelProperty(value = "目录介绍")
    private String introduce;

    /** 标签（主要是三级目录） */
    @ApiModelProperty(value = "标签(单双)")
    private String directoryTag;

    List<PicDirectoryListDTO> list;
}
