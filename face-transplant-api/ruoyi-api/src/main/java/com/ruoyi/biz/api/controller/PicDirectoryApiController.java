package com.ruoyi.biz.api.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.convert.PhotoAlbumConvert;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;
import com.ruoyi.biz.dto.PicDirectoryListDTO;
import com.ruoyi.biz.param.DirectoryParam;
import com.ruoyi.biz.service.IPhotoAlbumService;
import com.ruoyi.biz.service.IPicDirectoryService;
import com.ruoyi.biz.utils.PageUtil;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.framework.config.ServerConfig;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @author hzw
 * @date 2024/5/6
 */
@Api(tags = "目录接口")
@Slf4j
@RequestMapping("/api/directory")
@RestController
public class PicDirectoryApiController {


    private PhotoAlbumConvert photoAlbumConvert = PhotoAlbumConvert.INSTANCE;

    @Autowired
    private IPicDirectoryService picDirectoryService;

    @Autowired
    private IPhotoAlbumService photoAlbumService;


    /**
     * 查询列表,随机9张
     */
    @ApiOperation("根据三级目录查询模板列表")
    @GetMapping("/getPhotoAlbumlist/{directoryId}")
    public ApiResult<List<PhotoAlbumListDTO>> list(@PathVariable("directoryId") Long directoryId) throws Exception {
        DirectoryParam param = new DirectoryParam();
        // 判断是否是刷新
        Long userId = ApiContext.getCurrentUserId();
        param.setUserId(userId);
        param.setDirectoryId(directoryId);

        return ApiResult.success(photoAlbumService.selectPhotoAlbumIdList(param));
    }

    /**
     * 查询目录列列表
     */
    @ApiOperation("查询目录列表")
    @GetMapping("/getDirectorylist/{directoryId}")
    public ApiResult<PageInfo<PicDirectoryListDTO>> getDirectorylist(
            @ApiParam(name = "directoryId", value = "目录id-首页一级目录传0", required = true,defaultValue = "0")
            @PathVariable("directoryId") Long directoryId)
    {
        startPage();
        PageInfo<PicDirectoryListDTO> picDirectoryPageInfo =
                picDirectoryService.selectPicDirectoryApiList(directoryId);
        return ApiResult.success(picDirectoryPageInfo);
    }

    @ApiOperation("根据一级目录查询二级目录以及三级图片")
    @GetMapping("/{directoryId}")
    public ApiResult<PageInfo<PicDirectoryListDTO>> selectList(
            @ApiParam(name = "directoryId", value = "目录id-首页一级目录传0", required = true,defaultValue = "0")
            @PathVariable("directoryId") Long directoryId){
        startPage();
        return ApiResult.success(picDirectoryService.selectApiList(directoryId));
    }

    @ApiOperation("查询目录详情 ")
    @GetMapping("/getDirectory/{id}")
    public ApiResult<PicDirectory> getDirectory(@PathVariable("id") Long id){

        return ApiResult.success(picDirectoryService.selectPicDirectoryById(id));
    }


    @ApiOperation("查询目录-最新")
    @GetMapping("/getDirectorylist/now")
    public ApiResult<PageInfo<PicDirectoryListDTO>> getDirectorylistNow(){
        startPage();
        return ApiResult.success(picDirectoryService.selectPicDirectoryNowList());
    }

    @ApiOperation("查询目录-最热")
    @GetMapping("/getDirectorylist/host")
    public ApiResult<PageInfo<PicDirectoryListDTO>> getDirectorylistHost(){
        startPage();
        return ApiResult.success(picDirectoryService.selectPicDirectoryHostList());
    }




}
