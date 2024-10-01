package com.ruoyi.biz.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.IntegralLogMapper;
import com.ruoyi.biz.domain.IntegralLog;
import com.ruoyi.biz.service.IIntegralLogService;

/**
 * 积分变动日志Service业务层处理
 * 
 * @author hzw
 * @date 2024-08-10
 */
@Service
public class IntegralLogServiceImpl implements IIntegralLogService 
{
    @Autowired
    private IntegralLogMapper integralLogMapper;

    /**
     * 查询积分变动日志
     * 
     * @param id 积分变动日志主键
     * @return 积分变动日志
     */
    @Override
    public IntegralLog selectIntegralLogById(Long id)
    {
        return integralLogMapper.selectIntegralLogById(id);
    }

    /**
     * 查询积分变动日志列表
     * 
     * @param integralLog 积分变动日志
     * @return 积分变动日志
     */
    @Override
    public List<IntegralLog> selectIntegralLogList(IntegralLog integralLog)
    {
        return integralLogMapper.selectIntegralLogList(integralLog);
    }

    /**
     * 新增积分变动日志
     * 
     * @param integralLog 积分变动日志
     * @return 结果
     */
    @Override
    public int insertIntegralLog(IntegralLog integralLog)
    {
        integralLog.setCreateTime(DateUtils.getNowDate());
        return integralLogMapper.insertIntegralLog(integralLog);
    }

    /**
     * 修改积分变动日志
     * 
     * @param integralLog 积分变动日志
     * @return 结果
     */
    @Override
    public int updateIntegralLog(IntegralLog integralLog)
    {
        integralLog.setUpdateTime(DateUtils.getNowDate());
        return integralLogMapper.updateIntegralLog(integralLog);
    }

    /**
     * 批量删除积分变动日志
     * 
     * @param ids 需要删除的积分变动日志主键
     * @return 结果
     */
    @Override
    public int deleteIntegralLogByIds(Long[] ids)
    {
        return integralLogMapper.deleteIntegralLogByIds(ids);
    }

    /**
     * 删除积分变动日志信息
     * 
     * @param id 积分变动日志主键
     * @return 结果
     */
    @Override
    public int deleteIntegralLogById(Long id)
    {
        return integralLogMapper.deleteIntegralLogById(id);
    }
}
