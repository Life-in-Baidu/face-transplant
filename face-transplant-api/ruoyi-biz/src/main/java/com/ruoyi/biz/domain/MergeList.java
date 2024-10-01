package com.ruoyi.biz.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 融合组对象 biz_merge_list
 * 
 * @author ruoyi
 * @date 2024-05-06
 */
@Data
public class MergeList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 融合状态（0-融合中，1-融合成功） */
    @Excel(name = "融合状态", readConverterExp = "0=-融合中，1-融合成功,2-待生成")
    private Integer mergeState;

    /** 融合图片id集合 */
    @Excel(name = "融合图片id集合")
    private String mergeList;

    /** 融合代表图 */
    @Excel(name = "融合代表图")
    private String url;

    /** 普通积分 */
    @Excel(name = "普通积分")
    private Long price;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private Long priceRew;


    /** 标签（0-单人，1-多人） */
    @Excel(name = "标签", readConverterExp = "0=-单人，1-多人")
    private String directoryTag;

    /** 目录id */
    @Excel(name = "目录id")
    private Long directoryId;

    /**
     * 顺序
     */
    private Integer limitNum;

    private Integer number;
}
