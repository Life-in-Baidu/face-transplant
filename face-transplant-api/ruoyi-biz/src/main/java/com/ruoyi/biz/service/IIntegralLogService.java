package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.IntegralLog;

/**
 * 积分变动日志Service接口
 * 
 * @author hzw
 * @date 2024-08-10
 */
public interface IIntegralLogService 
{
    /**
     * 查询积分变动日志
     * 
     * @param id 积分变动日志主键
     * @return 积分变动日志
     */
    public IntegralLog selectIntegralLogById(Long id);

    /**
     * 查询积分变动日志列表
     * 
     * @param integralLog 积分变动日志
     * @return 积分变动日志集合
     */
    public List<IntegralLog> selectIntegralLogList(IntegralLog integralLog);

    /**
     * 新增积分变动日志
     * 
     * @param integralLog 积分变动日志
     * @return 结果
     */
    public int insertIntegralLog(IntegralLog integralLog);

    /**
     * 修改积分变动日志
     * 
     * @param integralLog 积分变动日志
     * @return 结果
     */
    public int updateIntegralLog(IntegralLog integralLog);

    /**
     * 批量删除积分变动日志
     * 
     * @param ids 需要删除的积分变动日志主键集合
     * @return 结果
     */
    public int deleteIntegralLogByIds(Long[] ids);

    /**
     * 删除积分变动日志信息
     * 
     * @param id 积分变动日志主键
     * @return 结果
     */
    public int deleteIntegralLogById(Long id);
}
