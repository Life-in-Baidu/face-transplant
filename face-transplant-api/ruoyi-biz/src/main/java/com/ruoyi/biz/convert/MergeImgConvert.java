package com.ruoyi.biz.convert;

import com.ruoyi.biz.domain.MergeImg;
import com.ruoyi.biz.dto.MeregeImgDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hzw
 * @date 2024/5/6
 */
@Mapper
public interface MergeImgConvert {

    MergeImgConvert INSTANCE  = Mappers.getMapper(MergeImgConvert.class);

    MeregeImgDTO bo2Dto (MergeImg mergeImg) ;

}
