package com.ruoyi.biz.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzw
 * @date 2024/5/5
 */
@ApiModel(value = "模板图片对象")
@Data
public class PhotoAlbumListDTO {
    /** id主键 */
    private Long id;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 图片地址 */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "模板URL")
    private String modelUrl;

    /** 使用次数 */
    @ApiModelProperty(value = "使用次数")
    private Long usageTimes;

    /** 身材标签（0-正常，1-胖，2-瘦） */
    @ApiModelProperty(value = "身材标签,0=-正常，1-胖，2-瘦")
    private Long figureTag;

    /** 目录id */
    @ApiModelProperty(value = "目录id")
    private Long directoryId;

    /** 普通积分 */
    @ApiModelProperty(value = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private Long rewIntegral;

    /** 图片类型（0-随机图片，1-基础图片，2-奖励图片） */
    @ApiModelProperty(value = "图片类型,0-随机图片，1-基础图片，2-奖励图片")
    private Integer imgType;
}
