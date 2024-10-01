package com.ruoyi.biz.param;

import com.ruoyi.biz.param.FaceFusionParam;
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
@Data
@ApiModel("融合参数")
public class FaceFusionListParam {

    List<FaceFusionParam> faceFusionParamList;

    @ApiModelProperty(value = "融合标识添加 1-是 0-否", example = "1")
    private Integer logoAdd;

    /** 身材标签（0-正常，1-胖，2-瘦） */
    @ApiModelProperty(value = "身材标签,0=-正常，1-胖，2-瘦")
    private Long figureTag;


    @ApiModelProperty("融合组id，如果没有加入备选则为空，加入则输入")
    private Long mergeListId;

    @ApiModelProperty("目录id-三级目录的id")
    private Long directoryId;

    /** 价格 */
    @ApiModelProperty(value = "价格")
    private Long price;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private Long priceRew;


}
