package com.ruoyi.biz.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.dto.DirectoryListDTO;
import com.ruoyi.biz.dto.PicDirectoryListDTO;

/**
 * 目录列Service接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface IPicDirectoryService 
{
    /**
     * 查询目录列
     * 
     * @param id 目录列主键
     * @return 目录列
     */
    public PicDirectory selectPicDirectoryById(Long id);

    /**
     * 查询目录列列表
     * 
     * @param picDirectory 目录列
     * @return 目录列集合
     */
    public List<PicDirectory> selectPicDirectoryList(PicDirectory picDirectory);

    /**
     * 新增目录列
     * 
     * @param picDirectory 目录列
     * @return 结果
     */
    public int insertPicDirectory(PicDirectory picDirectory);

    /**
     * 修改目录列
     * 
     * @param picDirectory 目录列
     * @return 结果
     */
    public int updatePicDirectory(PicDirectory picDirectory);

    /**
     * 批量删除目录列
     * 
     * @param ids 需要删除的目录列主键集合
     * @return 结果
     */
    public int deletePicDirectoryByIds(Long[] ids);

    /**
     * 删除目录列信息
     * 
     * @param id 目录列主键
     * @return 结果
     */
    public int deletePicDirectoryById(Long id);


    /**
     * 查询目录列列表
     *
     * @param directoryId 目录id
     * @return 目录列集合
     */
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryApiList(Long directoryId);

    /**
     * 查询最新目录列列表
     */
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryNowList();

    /**
     * 查询最热目录列列表
     */
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryHostList();

    /**
     * 查询目录列列表
     *
     * @param directoryId 目录id
     * @return 目录列集合
     */
    public PageInfo<PicDirectoryListDTO> selectApiList(Long directoryId);
}
