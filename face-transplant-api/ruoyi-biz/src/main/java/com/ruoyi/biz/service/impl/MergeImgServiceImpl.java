package com.ruoyi.biz.service.impl;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.convert.MergeImgConvert;
import com.ruoyi.biz.domain.*;
import com.ruoyi.biz.dto.MeregeImgDTO;
import com.ruoyi.biz.dto.MergeListDTO;
import com.ruoyi.biz.mapper.PhotoAlbumMapper;
import com.ruoyi.biz.param.DirectoryParam;
import com.ruoyi.biz.param.FaceFusionListParam;
import com.ruoyi.biz.param.FaceFusionParam;
import com.ruoyi.biz.service.*;
import com.ruoyi.biz.utils.FaceFusionUtils;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.MergeImgMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 融合图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@Slf4j
@Service
public class MergeImgServiceImpl implements IMergeImgService 
{

    @Autowired
    private IUserService userService;

    @Autowired
    private MergeImgMapper mergeImgMapper;

    @Autowired
    private IMergeListService mergeListService;

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;



    @Autowired
    private IIntegralLogService iIntegralLogService;

    /**
     * 查询融合图片
     * 
     * @param id 融合图片主键
     * @return 融合图片
     */
    @Override
    public MergeImg selectMergeImgById(Long id)
    {
        return mergeImgMapper.selectMergeImgById(id);
    }

    /**
     * 查询融合图片列表
     * 
     * @param mergeImg 融合图片
     * @return 融合图片
     */
    @Override
    public List<MergeImg> selectMergeImgList(MergeImg mergeImg)
    {
        return mergeImgMapper.selectMergeImgList(mergeImg);
    }

    /**
     * 新增融合图片
     * 
     * @param mergeImg 融合图片
     * @return 结果
     */
    @Override
    public int insertMergeImg(MergeImg mergeImg)
    {
        return mergeImgMapper.insertMergeImg(mergeImg);
    }

    /**
     * 修改融合图片
     * 
     * @param mergeImg 融合图片
     * @return 结果
     */
    @Override
    public int updateMergeImg(MergeImg mergeImg)
    {
        return mergeImgMapper.updateMergeImg(mergeImg);
    }

    /**
     * 批量删除融合图片
     * 
     * @param ids 需要删除的融合图片主键
     * @return 结果
     */
    @Override
    public int deleteMergeImgByIds(Long[] ids)
    {
        return mergeImgMapper.deleteMergeImgByIds(ids);
    }

    /**
     * 删除融合图片信息
     * 
     * @param id 融合图片主键
     * @return 结果
     */
    @Override
    public int deleteMergeImgById(Long id)
    {
        return mergeImgMapper.deleteMergeImgById(id);
    }

    @Transactional
    @Override
    public MergeListDTO MergeImg(FaceFusionListParam faceFusionListParam,Long userId) throws Exception {
        // 获取用户信息
        User user = userService.selectUserById(userId);


        List<FaceFusionParam> faceFusionParamList = faceFusionListParam.getFaceFusionParamList();

        // 默认正常图片，不是则进行替换
        if(faceFusionListParam.getFigureTag() != 0) {
            PhotoAlbum photoAlbum = new PhotoAlbum();
            photoAlbum.setFigureTag(faceFusionListParam.getFigureTag());
            // 替换
            for (FaceFusionParam param : faceFusionParamList) {
                photoAlbum.setStyleId(param.getId());
                PhotoAlbum photoAlbum1 = photoAlbumMapper.selectPhotoAlbum(photoAlbum);
                if (ObjectUtil.isEmpty(photoAlbum1)) {
                    throw new CustomException("没有相关图片身材类型");
                }
                param.setImgUrl(photoAlbum1.getImgUrl());
                param.setId(param.getId());
            }
        }
        // 检查用户积分是否满足
        if (!isIntegral(faceFusionListParam.getPrice(),faceFusionListParam.getPriceRew(),user)) {
            return null;
        }

        if (faceFusionParamList != null && faceFusionParamList.size() > 0) {
            MergeList mergeList = mergeListService.selectMergeListById(faceFusionListParam.getMergeListId());

            // 融合

             for (FaceFusionParam param : faceFusionParamList) {

                // 保存融合后图片
                MergeImg mergeImg = new MergeImg();
                mergeImg.setUserId(userId);
                mergeImg.setModelId(param.getId());
                mergeImg.setMergeState(0);
                mergeImg.setImgUrl(stringJoinerId(param.getUrl()));
                mergeImg.setMergeTime(new Date());
                mergeImg.setMergeListId(mergeList.getId());
                mergeImgMapper.insertMergeImg(mergeImg);
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                 try {

                     String merge = FaceFusionUtils.merge(param.getUrl(), param.getImgUrl(), faceFusionListParam.getLogoAdd());

                     mergeImg.setMergeUrl(merge);
                     mergeImg.setMergeState(1);
                     mergeImgMapper.updateMergeImg(mergeImg);
                 } catch (Exception e) {
                     log.info("融合失败");
                     mergeImg.setMergeState(2);
                     mergeImg.setMergeUrl(param.getImgUrl());
                     mergeImgMapper.updateMergeImg(mergeImg);
                 }
                    log.info("融合完成");
                    return "融合成功";
                });
             }




            mergeList.setMergeState(1);
            mergeListService.updateMergeList(mergeList);
            MergeListDTO mergeListDTO = BeanUtil.copyProperties(mergeList, MergeListDTO.class);
            // 返回融合组信息（不包括融合图片）
            return mergeListDTO;
        }
        return new MergeListDTO();
    }

    @Override
    public MeregeImgDTO MergeOneImg(MergeImg mergeImg) throws Exception {


        mergeImg.setMergeState(0);
        mergeImg.setMergeTime(new Date());
        mergeImgMapper.updateMergeImg(mergeImg);
        MergeList mergeList = mergeListService.selectMergeListById(mergeImg.getMergeListId());

        List<String> ids = Arrays.asList(mergeList.getMergeList().split(","));

        // 排除后随机抽取一张图片
        DirectoryParam param = new DirectoryParam();
        param.setFigureTag(0L);
        param.setImgType(1);
        param.setNumber(1L);
        param.setDirectoryId(mergeList.getDirectoryId());

        // 排除三次
        List<PhotoAlbum> photoAlbumListJL = photoAlbumMapper.selectPhotoAlbumRandList(param);
        int count = 0;
        while (count < 3) {
            int k = 0;
            for(String id : ids) {
                if(id.equals(photoAlbumListJL.get(0).getId().toString())) {
                    photoAlbumListJL = photoAlbumMapper.selectPhotoAlbumRandList(param);
                    k = 1;
                    break;
                }
            }
            if(k == 0) {
                break;
            }
            count ++;
        }



        // 转换数据
        String modelUrl = photoAlbumListJL.get(0).getImgUrl();
        List<String> urlList = Arrays.asList(mergeImg.getImgUrl().split(","));

        try {
            // 融合
            String merge = FaceFusionUtils.merge(urlList, modelUrl, 0);

            mergeImg.setMergeUrl(merge);
            mergeImg.setMergeState(1);
            mergeImgMapper.updateMergeImg(mergeImg);
        }catch (Exception e) {
            mergeImg.setMergeState(2);
            mergeImgMapper.updateMergeImg(mergeImg);
        }
        mergeImg.setMergeUrl(mergeImg.getMergeUrl());
        MeregeImgDTO meregeImgDTO = BeanUtil.copyProperties(mergeImg, MeregeImgDTO.class);

        return meregeImgDTO;
    }


    /**
     * 检查积分是否满足
     * @return
     */
    public Boolean isIntegral(Long pice, Long piceRew,User user) {
        // 计算积分是否足够
        Long ordIntegral = pice;
        Long rewIntegral = piceRew;

//        for (FaceFusionParam param : faceFusionParamList){
//            PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(param.getId());
//            rewIntegral += photoAlbum.getRewIntegral();
//            ordIntegral += photoAlbum.getOrdIntegral();
//
//            photoAlbum.setUsageTimes(photoAlbum.getUsageTimes() + 1);
//            photoAlbumService.updatePhotoAlbum(photoAlbum);
//        }


        if (user.getOrdIntegral() >= ordIntegral && user.getRewIntegral() >= rewIntegral) {
            // 消耗积分
            user.setRewIntegral(user.getRewIntegral() - rewIntegral);
            user.setOrdIntegral(user.getOrdIntegral() - ordIntegral);
            userService.updateUser(user);

            // 积分日志
            iIntegralLogService.insertIntegralLog(IntegralLog.builder()
                    .rewIntegral(0-rewIntegral)
                    .ordIntegral(0-ordIntegral)
                    .userId(user.getId())
                    .build());
            return true;
        }
        return false;
    }


    /**
     * 序列化字段
     * @return
     */
    public String stringJoinerId(List<String> urls) {
        // 序列化list,并保存到数据库
        StringJoiner joiner = new StringJoiner(",");
        for (String item : urls) {
            if (StringUtils.isEmpty(item)) {
                break;
            }
            joiner.add(item);
        }
        String urlString = joiner.toString();
        return urlString;
    }


}
