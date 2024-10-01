package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.MergeList;
import com.ruoyi.biz.dto.MergeBefoListDTO;
import com.ruoyi.biz.dto.MergeListDTO;

/**
 * 融合组Service接口
 * 
 * @author ruoyi
 * @date 2024-05-06
 */
public interface IMergeListService 
{
    /**
     * 查询融合组
     * 
     * @param id 融合组主键
     * @return 融合组
     */
    public MergeList selectMergeListById(Long id);

    /**
     * 查询融合组列表
     * 
     * @param mergeList 融合组
     * @return 融合组集合
     */
    public List<MergeList> selectMergeListList(MergeList mergeList);

    /**
     * 新增融合组
     * 
     * @param mergeList 融合组
     * @return 结果
     */
    public int insertMergeList(MergeList mergeList);

    /**
     * 修改融合组
     * 
     * @param mergeList 融合组
     * @return 结果
     */
    public int updateMergeList(MergeList mergeList);

    /**
     * 批量删除融合组
     * 
     * @param ids 需要删除的融合组主键集合
     * @return 结果
     */
    public int deleteMergeListByIds(Long[] ids);

    /**
     * 删除融合组信息
     * 
     * @param id 融合组主键
     * @return 结果
     */
    public int deleteMergeListById(Long id);



    /**
     * 根据id查询融合组信息
     */
    public MergeBefoListDTO selectMergeListDTOById(Long id);

    /**
     * 查询备选列表
     *
     * @param mergeList 融合组
     * @return 融合组集合
     */
    public List<MergeListDTO> selectMergeList(MergeList mergeList);

    /**
     * 查询融合列表
     *
     * @param mergeList 融合组
     * @return 融合组集合
     */
    public List<MergeListDTO> selectMergeBefoList(MergeList mergeList);


    public MergeBefoListDTO selectPhotoFigureTag(MergeList mergeList);
}
