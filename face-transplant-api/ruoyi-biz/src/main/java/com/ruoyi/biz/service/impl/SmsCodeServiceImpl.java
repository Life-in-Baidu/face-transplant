package com.ruoyi.biz.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.biz.domain.SmsCode;
import com.ruoyi.biz.mapper.SmsCodeMapper;
import com.ruoyi.biz.service.ISmsCodeService;
import com.ruoyi.biz.utils.SendMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 手机验证码Service业务层处理
 *
 * @author iven
 * @date 2020-10-20
 */
@Service
public class SmsCodeServiceImpl implements ISmsCodeService
{
    @Resource
    private SmsCodeMapper bizSmsCodeMapper;
    private final Integer smsSendLimit = 5;
    /**
     * 查询手机验证码
     *
     * @param id 手机验证码ID
     * @return 手机验证码
     */
    @Override
    public SmsCode selectBizSmsCodeById(Long id)
    {
        return bizSmsCodeMapper.selectBizSmsCodeById(id);
    }

    /**
     * 查询手机验证码列表
     *
     * @param bizSmsCode 手机验证码
     * @return 手机验证码
     */
    @Override
    public List<SmsCode> selectBizSmsCodeList(SmsCode bizSmsCode)
    {
        return bizSmsCodeMapper.selectBizSmsCodeList(bizSmsCode);
    }

    /**
     * 新增手机验证码
     *
     * @param bizSmsCode 手机验证码
     * @return 结果
     */
    @Override
    public int insertBizSmsCode(SmsCode bizSmsCode)
    {
        return bizSmsCodeMapper.insertBizSmsCode(bizSmsCode);
    }

    /**
     * 修改手机验证码
     *
     * @param bizSmsCode 手机验证码
     * @return 结果
     */
    @Override
    public int updateBizSmsCode(SmsCode bizSmsCode)
    {
        return bizSmsCodeMapper.updateBizSmsCode(bizSmsCode);
    }

    /**
     * 批量删除手机验证码
     *
     * @param ids 需要删除的手机验证码ID
     * @return 结果
     */
    @Override
    public int deleteBizSmsCodeByIds(Long[] ids)
    {
        return bizSmsCodeMapper.deleteBizSmsCodeByIds(ids);
    }

    /**
     * 删除手机验证码信息
     *
     * @param id 手机验证码ID
     * @return 结果
     */
    @Override
    public int deleteBizSmsCodeById(Long id)
    {
        return bizSmsCodeMapper.deleteBizSmsCodeById(id);
    }


    @Override
    public int sendSmsCode(String phone) {
        int currentSend = bizSmsCodeMapper.selectTodaySmsCount(phone);
        if (currentSend >= smsSendLimit){
            return 1; //超过当天最大短信发送次数（只计算无效次数）
        }
        SmsCode smsCode = bizSmsCodeMapper.selectByPhoneLatest(phone);
        if (smsCode!=null && ((System.currentTimeMillis() - smsCode.getAddTime().getTime())/1000/60)<5){
            return 2;
        }
        //生成短信验证码
        String code = RandomUtil.randomNumbers(6);
        //发送阿里云短信代替第三方短信
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        SendMsgUtil.sendMsg(map,phone);

        smsCode = new SmsCode();
        smsCode.setAddTime(new Date());
        smsCode.setCode(code);
        smsCode.setPhone(phone);
        smsCode.setUsed(0);
        bizSmsCodeMapper.insertBizSmsCode(smsCode);
        return 0;
    }

    @Override
    public boolean validCode(String phone, String code) {
        SmsCode smsCode = bizSmsCodeMapper.selectByPhoneAndCode(phone,code);
        if (ObjectUtil.isEmpty(smsCode)) return false;
        smsCode.setUsed(1);
        bizSmsCodeMapper.updateBizSmsCode(smsCode);
        return true;
    }

    @Override
    public int validSmsCode(String phone, String code) {
        SmsCode smsCode = bizSmsCodeMapper.selectByPhoneAndCode(phone,code);
        if (smsCode == null){
            //临时改下
            return 0;
            //return 1;
        }else{
            smsCode.setUsed(1);
            bizSmsCodeMapper.updateBizSmsCode(smsCode);
        }
        return 1;
    }
}
