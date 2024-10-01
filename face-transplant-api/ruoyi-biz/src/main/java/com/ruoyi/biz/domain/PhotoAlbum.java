package com.ruoyi.biz.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 相册对象 biz_photo_album
 *
 * @author ruoyi
 * @date 2024-06-13
 */
@Data
public class PhotoAlbum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;

    /** 使用次数 */
    @Excel(name = "使用次数")
    private Long usageTimes;

    /** 身材标签（0-正常，1-胖，2-瘦） */
    @Excel(name = "身材标签", readConverterExp = "0=-正常，1-胖，2-瘦")
    private Long figureTag;

    /** 所属目录id */
    @Excel(name = "所属目录id")
    private Long directoryId;

    /** 普通积分 */
    @Excel(name = "普通积分")
    private Long ordIntegral;

    /** 奖励积分 */
    @Excel(name = "奖励积分")
    private Long rewIntegral;

    /** 图片类型（0-随机图片，1-基础图片，2-奖励图片） */
    @Excel(name = "图片类型", readConverterExp = "0=-随机图片，1-基础图片，2-奖励图片")
    private Integer imgType;

    /** 同款图片id */
    @Excel(name = "同款图片id")
    private Long styleId;
}
