package com.ruoyi.biz.param;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @author hzw
 * @date 2024/7/3
 */
@Data
public class DirectoryParam {

    private Long directoryId;

    /** 图片类型（0-随机图片，1-基础图片，2-奖励图片） */
    private Integer imgType;

    private Long userId;

    /** 图片数量 */
    private Long number;

    private Long pageNum;

    /** 身材标签（0-正常，1-胖，2-瘦） */
    private Long figureTag;


}
