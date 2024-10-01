package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hzw
 * @date 2024/6/21
 */
@ApiModel(value = "融合前的相册项")
@Data
public class MergeBefoListDTO {


    /** id */
    private Long id;

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /** 融合状态（0-融合中，1-融合成功） */
    @ApiModelProperty(value = "融合状态,0=-融合中，1-融合成功")
    private Integer mergeState;

    @ApiModelProperty(value = "相除项url")
    private String url;


    @ApiModelProperty(value = "模板图片胖瘦情况：0-正常,1-胖，2-瘦")
    private List<Long> figureTag;

    List<PhotoAlbumListDTO> photoAlbumListDTOList;
}
