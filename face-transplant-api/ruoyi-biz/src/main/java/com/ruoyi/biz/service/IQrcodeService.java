package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.Qrcode;
import com.ruoyi.biz.dto.QrcodeDTO;

/**
 * 分享记录Service接口
 * 
 * @author hzw
 * @date 2024-06-18
 */
public interface IQrcodeService 
{
    /**
     * 查询分享记录
     * 
     * @param id 分享记录主键
     * @return 分享记录
     */
    public Qrcode selectQrcodeById(Long id);

    /**
     * 查询分享记录列表
     * 
     * @param qrcode 分享记录
     * @return 分享记录集合
     */
    public List<Qrcode> selectQrcodeList(Qrcode qrcode);

    /**
     * 新增分享记录
     * 
     * @param qrcode 分享记录
     * @return 结果
     */
    public int insertQrcode(Qrcode qrcode);

    /**
     * 修改分享记录
     * 
     * @param qrcode 分享记录
     * @return 结果
     */
    public int updateQrcode(Qrcode qrcode);

    /**
     * 批量删除分享记录
     * 
     * @param ids 需要删除的分享记录主键集合
     * @return 结果
     */
    public int deleteQrcodeByIds(Long[] ids);

    /**
     * 删除分享记录信息
     * 
     * @param id 分享记录主键
     * @return 结果
     */
    public int deleteQrcodeById(Long id);


    /**
     * 获取用户分享统计接口
     * * @return 分享记录
     */
    public QrcodeDTO getQrcodeNum(Long userId);
}
