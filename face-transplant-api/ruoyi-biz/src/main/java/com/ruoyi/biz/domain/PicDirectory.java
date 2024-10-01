package com.ruoyi.biz.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 目录列对象 biz_directory
 *
 * @author ruoyi
 * @date 2024-05-06
 */
@Data
public class PicDirectory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 目录id */
    private Long id;

    /** 上级目录id */
    @Excel(name = "上级目录id")
    private Long supDirectoryId;

    /** 目录图片url */
    @Excel(name = "目录图片url")
    private String directoryUrl;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String directoryName;

    /** 目录级别（1-一级目录，2-二级目录，3-三级目录） */
    @Excel(name = "目录级别", readConverterExp = "1=-一级目录，2-二级目录，3-三级目录")
    private String directoryLevel;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private BigDecimal priceRew;

    /** 标签（主要是三级目录） */
    @Excel(name = "标签", readConverterExp = "单人，双人")
    private String directoryTag;

    /** 使用次数 */
    @Excel(name = "使用次数")
    private Long usageTimes;

    /** 基础图片数量 */
    @Excel(name = "基础图片数量")
    private Long numberFoun;

    /** 奖励图片数量 */
    @Excel(name = "奖励图片数量")
    private Long numberRew;

    /** 目录介绍（主要是三级目录） */
    @Excel(name = "目录介绍", readConverterExp = "主=要是三级目录")
    private String introduce;

}
