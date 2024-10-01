package com.ruoyi.biz.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.domain.MergeImg;
import com.ruoyi.biz.dto.MeregeImgDTO;
import com.ruoyi.biz.dto.MergeListDTO;
import com.ruoyi.biz.param.FaceFusionListParam;

/**
 * 融合图片Service接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface IMergeImgService 
{
    /**
     * 查询融合图片
     * 
     * @param id 融合图片主键
     * @return 融合图片
     */
    public MergeImg selectMergeImgById(Long id);

    /**
     * 查询融合图片列表
     * 
     * @param mergeImg 融合图片
     * @return 融合图片集合
     */
    public List<MergeImg> selectMergeImgList(MergeImg mergeImg);

    /**
     * 新增融合图片
     *
     * @param mergeImg 融合图片
     * @return 结果
     */
    public int insertMergeImg(MergeImg mergeImg);

    /**
     * 修改融合图片
     * 
     * @param mergeImg 融合图片
     * @return 结果
     */
    public int updateMergeImg(MergeImg mergeImg);

    /**
     * 批量删除融合图片
     * 
     * @param ids 需要删除的融合图片主键集合
     * @return 结果
     */
    public int deleteMergeImgByIds(Long[] ids);

    /**
     * 删除融合图片信息
     * 
     * @param id 融合图片主键
     * @return 结果
     */
    public int deleteMergeImgById(Long id);


    /**
     * 融合图片
     */
    public MergeListDTO MergeImg(FaceFusionListParam faceFusionListParam,Long userId) throws  Exception;


    /**
     * 融合单张图片
     * @param mergeImg
     * @return
     * @throws Exception
     */
    public MeregeImgDTO MergeOneImg(MergeImg mergeImg) throws  Exception;
}
