package com.ruoyi.biz.convert;

import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author hzw
 * @date 2024/5/5
 */
@Mapper
public interface PhotoAlbumConvert {

    PhotoAlbumConvert INSTANCE  = Mappers.getMapper(PhotoAlbumConvert.class);

    PhotoAlbumListDTO bo2Dto (PhotoAlbum photoAlbum) ;



}
