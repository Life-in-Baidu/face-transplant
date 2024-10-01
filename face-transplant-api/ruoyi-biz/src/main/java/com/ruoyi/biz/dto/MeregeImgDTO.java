package com.ruoyi.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hzw
 * @date 2024/5/6
 */
@ApiModel(value = "融合后的单张图片")
@Data
public class MeregeImgDTO {
    /** 融合图片id */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 上传图片url */
    @ApiModelProperty(value = "上传图片url")
    private String imgUrl;

    /** 模板图片id */
    @ApiModelProperty(value = "模板图片id")
    private Long modelId;

    @ApiModelProperty(value = "模板URL")
    private String modelUrl;

    /** 融合图片url */
    @ApiModelProperty(value = "融合图片url")
    private String mergeUrl;

    /** 融合时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "融合时间")
    private Date mergeTime;

    /** 融合状态（0-融合中，1-融合成功） */
    @ApiModelProperty(value = "融合状态，0=-融合中，1-融合成功")
    private Integer mergeState;


}
