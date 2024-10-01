package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.SmsCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 手机验证码Mapper接口
 *
 * @author iven
 * @date 2020-10-20
 */
public interface SmsCodeMapper
{
    /**
     * 查询手机验证码
     *
     * @param id 手机验证码ID
     * @return 手机验证码
     */
    public SmsCode selectBizSmsCodeById(Long id);

    /**
     * 查询手机验证码列表
     *
     * @param bizSmsCode 手机验证码
     * @return 手机验证码集合
     */
    public List<SmsCode> selectBizSmsCodeList(SmsCode bizSmsCode);

    /**
     * 新增手机验证码
     *
     * @param bizSmsCode 手机验证码
     * @return 结果
     */
    public int insertBizSmsCode(SmsCode bizSmsCode);

    /**
     * 修改手机验证码
     *
     * @param bizSmsCode 手机验证码
     * @return 结果
     */
    public int updateBizSmsCode(SmsCode bizSmsCode);

    /**
     * 删除手机验证码
     *
     * @param id 手机验证码ID
     * @return 结果
     */
    public int deleteBizSmsCodeById(Long id);

    /**
     * 批量删除手机验证码
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizSmsCodeByIds(Long[] ids);

    /**
     * 查询今天发了多少短信
     * @param phone
     * @return
     */
    public int selectTodaySmsCount(String phone);

    /**
     * 查询是否有该验证码
     */
    public SmsCode selectByPhoneAndCode(@Param("phone") String phone, @Param("code") String code);

    /**
     * 查询最近一分钟是否发了短信
     */
    public SmsCode selectByPhoneLatest(String phone);
}
