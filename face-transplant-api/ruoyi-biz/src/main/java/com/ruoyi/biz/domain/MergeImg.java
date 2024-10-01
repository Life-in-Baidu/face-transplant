package com.ruoyi.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 融合图片对象 biz_merge_img
 *
 * @author ruoyi
 * @date 2024-05-06
 */
public class MergeImg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 融合图片id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 上传图片url */
    @Excel(name = "上传图片url")
    private String imgUrl;

    /** 模板图片id */
    @Excel(name = "模板图片id")
    private Long modelId;

    /** 融合图片url */
    @Excel(name = "融合图片url")
    private String mergeUrl;

    /** 融合时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "融合时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mergeTime;

    /** 融合状态（0-融合中，1-融合成功,2-融合失败） */
    @Excel(name = "融合状态", readConverterExp = "0=-融合中，1-融合成功")
    private Integer mergeState;

    /** 融合组id */
    @Excel(name = "融合组id")
    private Long mergeListId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }
    public void setModelId(Long modelId)
    {
        this.modelId = modelId;
    }

    public Long getModelId()
    {
        return modelId;
    }
    public void setMergeUrl(String mergeUrl)
    {
        this.mergeUrl = mergeUrl;
    }

    public String getMergeUrl()
    {
        return mergeUrl;
    }
    public void setMergeTime(Date mergeTime)
    {
        this.mergeTime = mergeTime;
    }

    public Date getMergeTime()
    {
        return mergeTime;
    }
    public void setMergeState(Integer mergeState)
    {
        this.mergeState = mergeState;
    }

    public Integer getMergeState()
    {
        return mergeState;
    }
    public void setMergeListId(Long mergeListId)
    {
        this.mergeListId = mergeListId;
    }

    public Long getMergeListId()
    {
        return mergeListId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("imgUrl", getImgUrl())
                .append("modelId", getModelId())
                .append("mergeUrl", getMergeUrl())
                .append("mergeTime", getMergeTime())
                .append("mergeState", getMergeState())
                .append("mergeListId", getMergeListId())
                .toString();
    }
}
