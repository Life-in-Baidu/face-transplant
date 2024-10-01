package com.ruoyi.biz.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hzw
 * @date 2024/5/6
 */
@ApiModel(value = "融合后的相册项")
@Data
public class MergeListDTO {

    /** id */
    private Long id;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 融合状态（0-融合中，1-融合成功） */
    @ApiModelProperty(value = "融合状态,0=-融合中，1-融合成功，2-待生成）")
    private Integer mergeState;

    @ApiModelProperty(value = "相除项url")
    private String url;

    /** 价格 */
    @ApiModelProperty(value = "价格")
    private Long price;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private Long priceRew;

    /** 标签（主要是三级目录） */
    @ApiModelProperty(value = "标签(单双)")
    private String directoryTag;

    /** 目录名称 */
    @ApiModelProperty(value = "目录名称")
    private String directoryName;

    @ApiModelProperty(value = "目录id")
    private Long directoryId;


    List<MeregeImgDTO> meregeImgDTOList;
}
