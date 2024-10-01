package com.ruoyi.biz.service;

import com.ruoyi.biz.domain.SmsCode;

import java.util.List;

/**
 * 手机验证码Service接口
 *
 * @author iven
 * @date 2020-10-20
 */
public interface ISmsCodeService
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
     * 批量删除手机验证码
     *
     * @param ids 需要删除的手机验证码ID
     * @return 结果
     */
    public int deleteBizSmsCodeByIds(Long[] ids);

    /**
     * 删除手机验证码信息
     *
     * @param id 手机验证码ID
     * @return 结果
     */
    public int deleteBizSmsCodeById(Long id);

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    public int sendSmsCode(String phone);

    /**
     * 校验手机验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public int validSmsCode(String phone, String code);

    /**
     * 校验手机验证码
     */
    public boolean validCode(String phone, String code);
}
