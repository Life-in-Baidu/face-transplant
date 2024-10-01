package com.ruoyi.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.convert.PhotoAlbumConvert;
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;
import com.ruoyi.biz.param.DirectoryParam;
import com.ruoyi.biz.service.IPicDirectoryService;
import com.ruoyi.biz.utils.PageUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.framework.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.PhotoAlbumMapper;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.service.IPhotoAlbumService;

/**
 * 相册Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@Slf4j
@Service
public class PhotoAlbumServiceImpl implements IPhotoAlbumService 
{
    private PhotoAlbumConvert photoAlbumConvert = PhotoAlbumConvert.INSTANCE;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    @Autowired
    private IPicDirectoryService picDirectoryService;


    @Autowired
    private RedisCache redisCache;

    /**
     * 查询相册
     * 
     * @param id 相册主键
     * @return 相册
     */
    @Override
    public PhotoAlbum selectPhotoAlbumById(Long id)
    {
        return photoAlbumMapper.selectPhotoAlbumById(id);
    }

    /**
     * 查询相册列表
     * 
     * @param photoAlbum 相册
     * @return 相册
     */
    @Override
    public List<PhotoAlbum> selectPhotoAlbumList(PhotoAlbum photoAlbum)
    {
        return photoAlbumMapper.selectPhotoAlbumList(photoAlbum);
    }

    /**
     * 新增相册
     * 
     * @param photoAlbum 相册
     * @return 结果
     */
    @Override
    public int insertPhotoAlbum(PhotoAlbum photoAlbum)
    {
        int i = photoAlbumMapper.insertPhotoAlbum(photoAlbum);
        if (ObjectUtil.isEmpty(photoAlbum.getStyleId())) {
            photoAlbum.setStyleId(photoAlbum.getId()); // 默认同款id为本身
            photoAlbumMapper.updatePhotoAlbum(photoAlbum);
        }
        return i;
    }

    /**
     * 修改相册
     * 
     * @param photoAlbum 相册
     * @return 结果
     */
    @Override
    public int updatePhotoAlbum(PhotoAlbum photoAlbum)
    {
        return photoAlbumMapper.updatePhotoAlbum(photoAlbum);
    }

    /**
     * 批量删除相册
     * 
     * @param ids 需要删除的相册主键
     * @return 结果
     */
    @Override
    public int deletePhotoAlbumByIds(Long[] ids)
    {
        return photoAlbumMapper.deletePhotoAlbumByIds(ids);
    }

    /**
     * 删除相册信息
     * 
     * @param id 相册主键
     * @return 结果
     */
    @Override
    public int deletePhotoAlbumById(Long id)
    {
        return photoAlbumMapper.deletePhotoAlbumById(id);
    }

    @Override
    public List<PhotoAlbumListDTO> selectPhotoAlbumIdList(DirectoryParam param) throws Exception {
        Long directoryId = param.getDirectoryId();
        // 查询目录
        PicDirectory picDirectory = picDirectoryService.selectPicDirectoryById(directoryId);
        param.setFigureTag(0L);
        // 查询奖励图片
        param.setImgType(2);
        param.setNumber(picDirectory.getNumberRew());
        param.setPageNum(0L);
        List<PhotoAlbum> photoAlbumListJL = photoAlbumMapper.selectPhotoAlbumRandList(param);

        // 抽取基础图片，以及判断是否是刷新
        Long userId = param.getUserId();
        /*Long usageTimes = 1L;
        if (!ObjectUtil.isEmpty(redisCache.getCacheObject(userId + "_" + directoryId))) {
            Long cacheObject = redisCache.getCacheObject(userId + "_" + directoryId);
            log.info("缓存：" + cacheObject);
            usageTimes = cacheObject;
            usageTimes ++;

        }
        log.info("第几页：" + usageTimes);
        param.setPageNum(picDirectory.getNumberFoun() * usageTimes);*/
        param.setImgType(1);
        param.setNumber(picDirectory.getNumberFoun());

        List<PhotoAlbum> photoAlbumList = photoAlbumMapper.selectPhotoAlbumRandList(param);
//        if (photoAlbumList.size() < param.getNumber()) {
//            usageTimes = 1L; // 初始为第一个
//            param.setPageNum(picDirectory.getNumberFoun() * usageTimes);
//            param.setNumber((usageTimes -1) * picDirectory.getNumberFoun());
//            photoAlbumList = photoAlbumMapper.selectPhotoAlbumRandList(param);
//        }
//        redisCache.setCacheObject(userId + "_" + directoryId, usageTimes, 30, TimeUnit.SECONDS);
        // 优化
        photoAlbumList.addAll(photoAlbumListJL);
        List<PhotoAlbumListDTO> pageInfoList = new ArrayList<>();
        for(PhotoAlbum photoAlbum : photoAlbumList) {
            PhotoAlbumListDTO photoAlbumListDTO = photoAlbumConvert.bo2Dto(photoAlbum);
            photoAlbumListDTO.setModelUrl( photoAlbumListDTO.getImgUrl());
            pageInfoList.add(photoAlbumListDTO);
        }

        return pageInfoList;
    }

    /**
     * 获取随机图片
     * @return
     */
    @Override
    public String getUrl() {
        return photoAlbumMapper.getUrl();
    }
}
