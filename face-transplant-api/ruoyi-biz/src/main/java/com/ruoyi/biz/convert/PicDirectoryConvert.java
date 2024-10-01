package com.ruoyi.biz.convert;

import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.dto.PicDirectoryListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hzw
 * @date 2024/5/5
 */
@Mapper
public interface PicDirectoryConvert {
    PicDirectoryConvert INSTANCE  = Mappers.getMapper(PicDirectoryConvert.class);

    PicDirectoryListDTO bo2Dto (PicDirectory picDirectory) ;
}
