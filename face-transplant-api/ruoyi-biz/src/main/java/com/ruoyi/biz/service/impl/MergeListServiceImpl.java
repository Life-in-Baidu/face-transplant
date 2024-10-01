package com.ruoyi.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.convert.MergeImgConvert;
import com.ruoyi.biz.convert.PhotoAlbumConvert;
import com.ruoyi.biz.domain.MergeImg;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.dto.MeregeImgDTO;
import com.ruoyi.biz.dto.MergeBefoListDTO;
import com.ruoyi.biz.dto.MergeListDTO;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;
import com.ruoyi.biz.mapper.MergeImgMapper;
import com.ruoyi.biz.mapper.PhotoAlbumMapper;
import com.ruoyi.biz.param.DirectoryParam;
import com.ruoyi.biz.service.IPhotoAlbumService;
import com.ruoyi.biz.service.IPicDirectoryService;
import com.ruoyi.biz.utils.PageUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.MergeListMapper;
import com.ruoyi.biz.domain.MergeList;
import com.ruoyi.biz.service.IMergeListService;

/**
 * 融合组Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-06
 */
@Service
public class MergeListServiceImpl implements IMergeListService 
{
    private PhotoAlbumConvert photoAlbumConvert = PhotoAlbumConvert.INSTANCE;

    @Autowired
    private MergeListMapper mergeListMapper;

    @Autowired
    private MergeImgMapper mergeImgMapper;

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IPicDirectoryService picDirectoryService;

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    /**
     * 查询融合组
     * 
     * @param id 融合组主键
     * @return 融合组
     */
    @Override
    public MergeList selectMergeListById(Long id)
    {
        return mergeListMapper.selectMergeListById(id);
    }

    /**
     * 查询融合组列表
     * 
     * @param mergeList 融合组
     * @return 融合组
     */
    @Override
    public List<MergeList> selectMergeListList(MergeList mergeList)
    {
        return mergeListMapper.selectMergeListList(mergeList);
    }

    /**
     * 新增融合组
     * 
     * @param mergeList 融合组
     * @return 结果
     */
    @Override
    public int insertMergeList(MergeList mergeList)
    {
        mergeList.setCreateTime(DateUtils.getNowDate());
        return mergeListMapper.insertMergeList(mergeList);
    }

    /**
     * 修改融合组
     * 
     * @param mergeList 融合组
     * @return 结果
     */
    @Override
    public int updateMergeList(MergeList mergeList)
    {
        return mergeListMapper.updateMergeList(mergeList);
    }

    /**
     * 批量删除融合组
     * 
     * @param ids 需要删除的融合组主键
     * @return 结果
     */
    @Override
    public int deleteMergeListByIds(Long[] ids)
    {
        return mergeListMapper.deleteMergeListByIds(ids);
    }

    /**
     * 删除融合组信息
     * 
     * @param id 融合组主键
     * @return 结果
     */
    @Override
    public int deleteMergeListById(Long id)
    {
        return mergeListMapper.deleteMergeListById(id);
    }

    @Override
    public MergeBefoListDTO selectMergeListDTOById(Long id) {
        MergeList mergeList = mergeListMapper.selectMergeListById(id);
        MergeBefoListDTO mergeBefoListDTO = BeanUtil.copyProperties(mergeList, MergeBefoListDTO.class);


        // 反序列化
        String serializedData = mergeList.getMergeList();
        String[] items = serializedData.split(",");
        List<String> retrievedList = Arrays.asList(items);
        // 查询返回数据
        List<PhotoAlbumListDTO> list = new ArrayList<>();
        for (int i = 0; i < retrievedList.size(); i++) {
            PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(Long.parseLong(retrievedList.get(i)));
            list.add(photoAlbumConvert.bo2Dto(photoAlbum));
        }


//        PageInfo<MeregeImgDTO> meregeImgDTOPageInfo = PageUtil.convertPageInfo2PageDTO(mergeImgList, MergeImgConvert.INSTANCE::bo2Dto);
//        List<MeregeImgDTO> list = meregeImgDTOPageInfo.getList();
//        list.stream().forEach(meregeImgDTO -> {
//            meregeImgDTO.setMergeUrl(serverConfig.getUrl() + meregeImgDTO.getMergeUrl());
//            if (meregeImgDTO.getMergeState() == 2) {
//                PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(meregeImgDTO.getModelId());
//
//                meregeImgDTO.setModelUrl(serverConfig.getUrl() + photoAlbum.getImgUrl());
//            }
//        });
        mergeBefoListDTO.setPhotoAlbumListDTOList(list);
        return mergeBefoListDTO;
    }

    @Override
    public List<MergeListDTO> selectMergeList(MergeList mergeList) {
        mergeList.setMergeState(2);
        List<MergeList> mergeListList = mergeListMapper.selectMergeListStateList(mergeList);
        List<MergeListDTO> mergeListDTOList = new ArrayList<>();

        // 查询待生成中的模板列表信息
        for(MergeList me : mergeListList) {
            // 反序列化
            String serializedData = me.getMergeList();
            String[] items = serializedData.split(",");
            List<String> retrievedList = Arrays.asList(items);
            MergeListDTO mergeListDTO = BeanUtil.copyProperties(me, MergeListDTO.class);

            // 查询目录名称
            PicDirectory picDirectory = picDirectoryService.selectPicDirectoryById(me.getDirectoryId());
            mergeListDTO.setDirectoryName(picDirectory.getDirectoryName());
            mergeListDTO.setDirectoryId(picDirectory.getId());
            // 查询融合组图片列表
           /* List<MeregeImgDTO> list = new ArrayList<>();

            retrievedList.stream().forEach(s -> {
                PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(Long.valueOf(s));
                MeregeImgDTO meregeImgDTO = new MeregeImgDTO();
                meregeImgDTO.setId(photoAlbum.getId());
                meregeImgDTO.setModelUrl(serverConfig.getUrl() + photoAlbum.getImgUrl());
                meregeImgDTO.setImgUrl(serverConfig.getUrl() + photoAlbum.getImgUrl());
                list.add(meregeImgDTO);
            });

            mergeListDTO.setMeregeImgDTOList(list);*/
            mergeListDTOList.add(mergeListDTO);
        }
        return mergeListDTOList;
    }

    @Override
    public List<MergeListDTO> selectMergeBefoList(MergeList mergeList) {
        mergeList.setMergeState(1);
        List<MergeList> mergeListList = mergeListMapper.selectMergeListStateList(mergeList);
        List<MergeListDTO> mergeListDTOList = new ArrayList<>();

        for(MergeList me : mergeListList) {
            MergeListDTO mergeListDTO = new MergeListDTO();
            mergeListDTO.setId(me.getId());

            // 查询目录名称
            PicDirectory picDirectory = picDirectoryService.selectPicDirectoryById(me.getDirectoryId());
            mergeListDTO.setDirectoryName(picDirectory.getDirectoryName());

            List<MergeImg> mergeImgList = mergeImgMapper.selectMergeImgIdList(me.getId());
            List<MeregeImgDTO> list = PageUtil.convertPageInfo2PageDTO(mergeImgList, MergeImgConvert.INSTANCE::bo2Dto).getList();
            list.stream().forEach(meregeImgDTO -> {

                meregeImgDTO.setMergeUrl(meregeImgDTO.getMergeUrl());
                // 融合失败模板
//                if (meregeImgDTO.getMergeState() == 2) {
//                    PhotoAlbum photoAlbum = photoAlbumService.selectPhotoAlbumById(meregeImgDTO.getModelId());
//                    meregeImgDTO.setModelUrl(serverConfig.getUrl() + photoAlbum.getImgUrl());
//                }
            });
            mergeListDTO.setMeregeImgDTOList(list);
            mergeListDTOList.add(mergeListDTO);
        }

        return mergeListDTOList;
    }

    @Override
    public MergeBefoListDTO selectPhotoFigureTag(MergeList mergeList) {
        MergeBefoListDTO mergeBefoListDTO = new MergeBefoListDTO();
        List<Long> figureTagList = new ArrayList<>();

        DirectoryParam param = new DirectoryParam();
        param.setDirectoryId(mergeList.getDirectoryId());
        param.setNumber(1L);
        for (int i = 0; i <= 2; i++) {
            param.setFigureTag(Long.valueOf(i));
            List<PhotoAlbum> photoAlbumList = photoAlbumMapper.selectPhotoAlbumRandList(param);
            if (photoAlbumList.size() > 0) {
                figureTagList.add(Long.valueOf(i));
            }
        }
        mergeBefoListDTO.setFigureTag(figureTagList);



        return mergeBefoListDTO;
    }

    /**
     * 保存融合组信息
     * @param mergeList
     * @return
     */
    public void saveMergeImg (MergeList mergeList){

        List<MergeImg> mergeImgList = mergeImgMapper.selectMergeImgIdList(mergeList.getId());
        List<String> list = new ArrayList<>();
        mergeImgList.stream().forEach(mergeImg1 -> {
            list.add(String.valueOf(mergeImg1.getId()));
        });
        // 序列化list,并保存到数据库
        StringJoiner joiner = new StringJoiner(",");
        for (String item : list) {
            joiner.add(item);
        }
        String MergeImgId = joiner.toString();
        mergeList.setMergeList(MergeImgId);
        mergeListMapper.updateMergeList(mergeList);
    }
}
