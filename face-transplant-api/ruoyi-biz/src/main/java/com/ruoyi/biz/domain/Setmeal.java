package com.ruoyi.biz.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套餐对象 biz_setmeal
 *
 * @author ruoyi
 * @date 2024-06-11
 */
@Data
public class Setmeal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐Code */
    private Long mealId;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String mealName;

    /** 套餐价格 */
    @Excel(name = "套餐价格")
    private Integer mealPrice;

    /** 套餐描述 */
    @Excel(name = "套餐描述")
    private String mealDescribe;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 赠送普通积分 */
    @Excel(name = "赠送普通积分")
    private Integer bonus;

    /** 普通积分 */
    @Excel(name = "普通积分")
    private Integer validNum;

    /** 有效天数 */
    @Excel(name = "有效天数")
    private Integer validDay;

    /** 控制上线（0-未上线，1-上线） */
    @Excel(name = "控制上线", readConverterExp = "0=-未上线，1-上线")
    private Integer control;


}
