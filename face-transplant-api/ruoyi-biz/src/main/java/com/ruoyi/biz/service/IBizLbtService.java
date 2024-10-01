package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.BizLbt;

/**
 * 轮播图Service接口
 *
 * @author ruoyi
 * @date 2024-04-26
 */
public interface IBizLbtService
{
    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    public BizLbt selectBizLbtById(Long id);

    /**
     * 查询轮播图列表
     *
     * @param bizLbt 轮播图
     * @return 轮播图集合
     */
    public List<BizLbt> selectBizLbtList(BizLbt bizLbt);

    /**
     * 新增轮播图
     *
     * @param bizLbt 轮播图
     * @return 结果
     */
    public int insertBizLbt(BizLbt bizLbt);

    /**
     * 修改轮播图
     *
     * @param bizLbt 轮播图
     * @return 结果
     */
    public int updateBizLbt(BizLbt bizLbt);

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteBizLbtByIds(Long[] ids);

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    public int deleteBizLbtById(Long id);
}
