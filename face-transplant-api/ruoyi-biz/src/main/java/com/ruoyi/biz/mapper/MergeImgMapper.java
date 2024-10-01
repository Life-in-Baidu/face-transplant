package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.MergeImg;

/**
 * 融合图片Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface MergeImgMapper 
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
     * 删除融合图片
     * 
     * @param id 融合图片主键
     * @return 结果
     */
    public int deleteMergeImgById(Long id);

    /**
     * 批量删除融合图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMergeImgByIds(Long[] ids);


    /**
     * 根据融合组id查询融合图片列表
     * @return 融合图片集合
     */
    public List<MergeImg> selectMergeImgIdList(Long mergeListId);

    /**
     * 查询融合图片
     * @return 融合图片
     */
    public MergeImg selectByModelId(Long modelId);
}
