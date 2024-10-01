package com.ruoyi.biz.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author juiwi
 */
@ApiModel(value = "人脸融合参数")
@Data
public class FaceFusionParam {

    @ApiModelProperty(value = "用户上传图片", required = true, example = "https://pic.rmb.bdstatic.com/bjh/events/e8d967c6c7ac8d0f2b6b8bc7b40240184034.png@h_1280")
    private List<String> url;

    @ApiModelProperty(value = "模板id", example = "1")
    private Long id;

    @ApiModelProperty(value = "模板图片", example = "https://b0.bdstatic.com/ugc/c9FRKxIruCVsIGgzlCuYYQf0d85622e0aee89ebd73d1b6bbd66541.jpg@h_1280")
    private String imgUrl;

    @ApiModelProperty(value = "融合标识添加 1-是 0-否", example = "1")
    private Integer logoAdd;

    @ApiModelProperty("融合图片id，单个图片融合需要")
    private Long mergeId;


}
