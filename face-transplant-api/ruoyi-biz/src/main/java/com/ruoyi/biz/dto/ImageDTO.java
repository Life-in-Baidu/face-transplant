package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xph
 * @ClassName ImageDTO
 * @Description 图片数据
 * @date 2023/4/18
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "图片数据")
@Data
public class ImageDTO {

    @ApiModelProperty(value = "base64")
    private String base64Code;

    @ApiModelProperty(value = "刷新时间（毫秒）")
    private Long flushTime;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "url")
    private String url;

}
