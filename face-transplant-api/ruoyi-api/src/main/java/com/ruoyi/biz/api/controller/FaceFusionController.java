package com.ruoyi.biz.api.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.domain.MergeImg;
import com.ruoyi.biz.domain.MergeList;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.dto.MeregeImgDTO;
import com.ruoyi.biz.dto.MergeBefoListDTO;
import com.ruoyi.biz.dto.MergeListDTO;
import com.ruoyi.biz.param.FaceFusionListParam;
import com.ruoyi.biz.param.FaceFusionParam;
import com.ruoyi.biz.service.IMergeImgService;
import com.ruoyi.biz.service.IMergeListService;
import com.ruoyi.biz.service.IPhotoAlbumService;
import com.ruoyi.biz.service.IPicDirectoryService;
import com.ruoyi.biz.utils.FaceFusionUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.framework.config.ServerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @author :juiwi
 * @date :2024/4/23 10:31
 * @description :some description
 */
@Api(tags = "人脸融合接口")
@Slf4j
@RequestMapping("/api/faceFusion")
@RestController
public class FaceFusionController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IMergeImgService mergeImgService;

    @Autowired
    private IMergeListService mergeListService;

    @Autowired
    private IPhotoAlbumService photoAlbumService;


    @Autowired
    private IPicDirectoryService picDirectoryService;

    @ApiOperation("融合单张图片")
    @PostMapping("/mergeId")
    public ApiResult<MeregeImgDTO> getMergeImg(@RequestBody FaceFusionParam param) throws Exception {
        // 保存融合后图片
        MergeImg mergeImg = mergeImgService.selectMergeImgById(param.getMergeId());
        return ApiResult.success(mergeImgService.MergeOneImg(mergeImg));
    }

    @ApiOperation("融合")
    @PostMapping("/merge")
    public ApiResult<MergeListDTO> faceFusion(@RequestBody FaceFusionListParam param) throws Exception {
        Long userId = ApiContext.getCurrentUserId();
        // 未保存待生成列表，则直接生成融合组（待生成）
        if (param.getMergeListId() == 0 || ObjectUtil.isEmpty(param.getMergeListId())) {
            log.info("未加入备选，生成备选列表");
            MergeList mergeList = new MergeList();
            mergeList.setUserId(userId);
            // 目前直接成功
            mergeList.setMergeState(0);
            String mergeImgId = stringJoinerId(param);
            mergeList.setMergeList(mergeImgId);
            mergeList.setDirectoryId(param.getDirectoryId());
            mergeList.setDirectoryTag(picDirectoryService
                    .selectPicDirectoryById(param.getDirectoryId()).getDirectoryTag());
            mergeList.setPrice(param.getPrice());
            mergeList.setPriceRew(param.getPriceRew());
            mergeListService.insertMergeList(mergeList);
            param.setMergeListId(mergeList.getId());
        }
        MergeListDTO mergeListDTO = mergeImgService.MergeImg(param, userId);
        if (ObjectUtil.isNull(mergeListDTO)) {
            return ApiResult.error("积分不足，请充值！");
        }
        return ApiResult.success(mergeListDTO);
    }



    @ApiOperation("查询生成列表")
    @GetMapping("/mergeList")
    public ApiResult<PageInfo<MergeListDTO>> listPage()
    {
        Long userId = ApiContext.getCurrentUserId();
        MergeList mergeList = new MergeList();
        mergeList.setUserId(userId);
        mergeList.setLimitNum(1);
        List<MergeListDTO> list = mergeListService.selectMergeBefoList(mergeList);
        PageInfo<MergeListDTO> pageInfo = new PageInfo<>(list);
        return ApiResult.success(pageInfo);
    }



    @ApiOperation("加入备选")
    @PostMapping
    public ApiResult add(@RequestBody FaceFusionListParam param)
    {
        Long userId = ApiContext.getCurrentUserId();
        // 保存融合组
        MergeList mergeList = new MergeList();
        mergeList.setUserId(userId);
        mergeList.setMergeState(2);
        String MergeImgId = stringJoinerId(param);
        // 设置图片组url
        FaceFusionParam faceFusionParam = param.getFaceFusionParamList().get(0);
        PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(faceFusionParam.getId());
        mergeList.setUrl(photoAlbum.getImgUrl());
        // 保存图片组
        mergeList.setMergeList(MergeImgId);
        mergeList.setPrice(param.getPrice());
        mergeList.setPriceRew(param.getPriceRew());
        mergeList.setDirectoryId(param.getDirectoryId());
        mergeList.setDirectoryTag(picDirectoryService.selectPicDirectoryById(param.getDirectoryId()).getDirectoryTag());
        mergeListService.insertMergeList(mergeList);
        log.info("加入备选成功");
        return ApiResult.success();
    }

    /**
     * 查询融合组列表
     */
    @ApiOperation("查询备选列表（待生成）")
    @GetMapping("/list")
    public ApiResult<PageInfo<MergeListDTO>> list()
    {
        Long userId = ApiContext.getCurrentUserId();
        MergeList mergeList = new MergeList();
        mergeList.setUserId(userId);
        startPage();
        List<MergeListDTO> list = mergeListService.selectMergeList(mergeList);
        PageInfo<MergeListDTO> pageInfo = new PageInfo<>(list);
        log.info("查询备选列表" + list);
        return ApiResult.success(pageInfo);
    }

    @ApiOperation("根据融合组id查询融合组信息")
    @GetMapping("/list/{id}")
    public ApiResult<MergeBefoListDTO> list(@PathVariable("id") Long id) {
        return ApiResult.success(mergeListService.selectMergeListDTOById(id));
    }

    @ApiOperation("根据融合组id查询模板情况")
    @GetMapping("/mergeList/{id}")
    public ApiResult<MergeBefoListDTO> mergeList(@PathVariable("id") Long id) {
        MergeList mergeList = mergeListService.selectMergeListById(id);
        return ApiResult.success(mergeListService.selectPhotoFigureTag(mergeList));
    }




    /**
     * 序列化字段
     * @param param
     * @return
     */
    public String stringJoinerId(FaceFusionListParam param) {
        List<String> list = new ArrayList<>();
        param.getFaceFusionParamList().stream().forEach(faceFusionParam -> {
            list.add(String.valueOf(faceFusionParam.getId()));
        });
        // 序列化list,并保存到数据库
        StringJoiner joiner = new StringJoiner(",");
        for (String item : list) {
            joiner.add(item);
        }
        String mergeImgId = joiner.toString();
        return mergeImgId;
    }
}
