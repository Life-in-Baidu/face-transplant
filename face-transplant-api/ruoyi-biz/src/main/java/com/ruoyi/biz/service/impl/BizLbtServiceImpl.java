package com.ruoyi.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.BizLbtMapper;
import com.ruoyi.biz.domain.BizLbt;
import com.ruoyi.biz.service.IBizLbtService;

/**
 * 轮播图Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class BizLbtServiceImpl implements IBizLbtService
{
    @Autowired
    private BizLbtMapper bizLbtMapper;

    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    @Override
    public BizLbt selectBizLbtById(Long id)
    {
        return bizLbtMapper.selectBizLbtById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param bizLbt 轮播图
     * @return 轮播图
     */
    @Override
    public List<BizLbt> selectBizLbtList(BizLbt bizLbt)
    {
        return bizLbtMapper.selectBizLbtList(bizLbt);
    }

    /**
     * 新增轮播图
     *
     * @param bizLbt 轮播图
     * @return 结果
     */
    @Override
    public int insertBizLbt(BizLbt bizLbt)
    {
        return bizLbtMapper.insertBizLbt(bizLbt);
    }

    /**
     * 修改轮播图
     *
     * @param bizLbt 轮播图
     * @return 结果
     */
    @Override
    public int updateBizLbt(BizLbt bizLbt)
    {
        return bizLbtMapper.updateBizLbt(bizLbt);
    }

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBizLbtByIds(Long[] ids)
    {
        return bizLbtMapper.deleteBizLbtByIds(ids);
    }

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBizLbtById(Long id)
    {
        return bizLbtMapper.deleteBizLbtById(id);
    }
}
