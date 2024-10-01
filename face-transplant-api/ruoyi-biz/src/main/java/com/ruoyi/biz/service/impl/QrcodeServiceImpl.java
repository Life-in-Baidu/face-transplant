package com.ruoyi.biz.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.biz.dto.QrcodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.QrcodeMapper;
import com.ruoyi.biz.domain.Qrcode;
import com.ruoyi.biz.service.IQrcodeService;

/**
 * 分享记录Service业务层处理
 * 
 * @author hzw
 * @date 2024-06-18
 */
@Service
public class QrcodeServiceImpl implements IQrcodeService 
{
    @Autowired
    private QrcodeMapper qrcodeMapper;

    /**
     * 查询分享记录
     * 
     * @param id 分享记录主键
     * @return 分享记录
     */
    @Override
    public Qrcode selectQrcodeById(Long id)
    {
        return qrcodeMapper.selectQrcodeById(id);
    }

    /**
     * 查询分享记录列表
     * 
     * @param qrcode 分享记录
     * @return 分享记录
     */
    @Override
    public List<Qrcode> selectQrcodeList(Qrcode qrcode)
    {
        return qrcodeMapper.selectQrcodeList(qrcode);
    }

    /**
     * 新增分享记录
     * 
     * @param qrcode 分享记录
     * @return 结果
     */
    @Override
    public int insertQrcode(Qrcode qrcode)
    {
        return qrcodeMapper.insertQrcode(qrcode);
    }

    /**
     * 修改分享记录
     * 
     * @param qrcode 分享记录
     * @return 结果
     */
    @Override
    public int updateQrcode(Qrcode qrcode)
    {
        return qrcodeMapper.updateQrcode(qrcode);
    }

    /**
     * 批量删除分享记录
     * 
     * @param ids 需要删除的分享记录主键
     * @return 结果
     */
    @Override
    public int deleteQrcodeByIds(Long[] ids)
    {
        return qrcodeMapper.deleteQrcodeByIds(ids);
    }

    /**
     * 删除分享记录信息
     * 
     * @param id 分享记录主键
     * @return 结果
     */
    @Override
    public int deleteQrcodeById(Long id)
    {
        return qrcodeMapper.deleteQrcodeById(id);
    }

    @Override
    public QrcodeDTO getQrcodeNum(Long userId) {
        Qrcode qrcode = Qrcode.builder().userId(userId).build();
        List<Qrcode> qrcodes = qrcodeMapper.selectQrcodeList(qrcode);

        QrcodeDTO qrcodeDTO = new QrcodeDTO();
        qrcodeDTO.setNumber(qrcodes.size());
        Long collect = qrcodes.stream().map(code -> code.getRewIntegral()).collect(Collectors.summingLong(Long::longValue));
        qrcodeDTO.setSum(collect);
        return qrcodeDTO;
    }
}
