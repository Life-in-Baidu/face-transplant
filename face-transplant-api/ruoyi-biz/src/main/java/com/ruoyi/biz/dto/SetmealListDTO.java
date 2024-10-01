package com.ruoyi.biz.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐列表对象 biz_setmeal
 *
 * @author czc
 * @date 2021-12-30
 */
@Data
@ApiModel("套餐列表对象")
public class SetmealListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 套餐Code */
    @ApiModelProperty(value = "套餐Code")
    private Long mealId;

    /** 套餐名称 */
    @ApiModelProperty(value = "套餐名称")
    private String mealName;

    /** 套餐价格 */
    @ApiModelProperty(value = "套餐价格")
    private Integer mealPrice;

    /** 套餐描述 */
    @ApiModelProperty(value = "套餐描述")
    private String mealDescribe;

    /** 赠送普通积分 */
    @ApiModelProperty(value = "赠送普通积分")
    private Integer bonus;

    /** 普通积分 */
    @ApiModelProperty(value = "普通积分")
    private Integer validNum;
}
