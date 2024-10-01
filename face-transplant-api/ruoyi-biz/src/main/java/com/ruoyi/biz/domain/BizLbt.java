package com.ruoyi.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 biz_lbt
 *
 * @author ruoyi
 * @date 2024-04-26
 */
public class BizLbt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id主键 */
    private Long id;

    /** 图片链接 */
    @Excel(name = "图片链接")
    private String imageUrl;

    /** 标题 */
    @Excel(name = "标题")
    private String caption;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    public String getCaption()
    {
        return caption;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imageUrl", getImageUrl())
            .append("caption", getCaption())
            .toString();
    }
}
