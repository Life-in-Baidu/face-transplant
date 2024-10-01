package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 话题对象 biz_moment
 *
 * @author shijieming
 * @date 2021-02-14
 */
@ApiModel(value = "上传结果")
@Data
public class UploadDTO
{
    private static final long serialVersionUID = 1L;

    /** 标签 */
    @ApiModelProperty(value = "文件名")
    private String fileName;

    /** 文字 */
    @ApiModelProperty(value = "文件地址")
    private String url;

}
