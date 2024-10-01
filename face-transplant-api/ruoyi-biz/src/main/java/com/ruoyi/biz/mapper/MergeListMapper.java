package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.MergeList;

/**
 * 融合组Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-06
 */
public interface MergeListMapper 
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
     * 删除融合组
     * 
     * @param id 融合组主键
     * @return 结果
     */
    public int deleteMergeListById(Long id);

    /**
     * 批量删除融合组
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMergeListByIds(Long[] ids);

    /**
     * 查询为融合组列表
     *
     * @param mergeList 融合组
     * @return 融合组集合
     */
    public List<MergeList> selectMergeListStateList(MergeList mergeList);
}
