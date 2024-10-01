package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.IntegralLog;

/**
 * 积分变动日志Mapper接口
 * 
 * @author hzw
 * @date 2024-08-10
 */
public interface IntegralLogMapper 
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
     * 删除积分变动日志
     * 
     * @param id 积分变动日志主键
     * @return 结果
     */
    public int deleteIntegralLogById(Long id);

    /**
     * 批量删除积分变动日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIntegralLogByIds(Long[] ids);
}
