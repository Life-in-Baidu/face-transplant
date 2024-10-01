package com.ruoyi.biz.service.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.convert.PicDirectoryConvert;
import com.ruoyi.biz.dto.DirectoryListDTO;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;
import com.ruoyi.biz.dto.PicDirectoryListDTO;
import com.ruoyi.biz.service.IPhotoAlbumService;
import com.ruoyi.biz.utils.PageUtil;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.PicDirectoryMapper;
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.service.IPicDirectoryService;

/**
 * 目录列Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@Service
public class PicDirectoryServiceImpl implements IPicDirectoryService 
{
    private PicDirectoryConvert picDirectoryConvert = PicDirectoryConvert.INSTANCE;

    @Autowired
    private PicDirectoryMapper picDirectoryMapper;

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    /**
     * 查询目录列
     * 
     * @param id 目录列主键
     * @return 目录列
     */
    @Override
    public PicDirectory selectPicDirectoryById(Long id)
    {
        return picDirectoryMapper.selectPicDirectoryById(id);
    }

    /**
     * 查询目录列列表
     * 
     * @param picDirectory 目录列
     * @return 目录列
     */
    @Override
    public List<PicDirectory> selectPicDirectoryList(PicDirectory picDirectory)
    {
        return picDirectoryMapper.selectPicDirectoryList(picDirectory);
    }

    /**
     * 新增目录列
     * 
     * @param picDirectory 目录列
     * @return 结果
     */
    @Override
    public int insertPicDirectory(PicDirectory picDirectory)
    {
        picDirectory.setCreateTime(DateUtils.getNowDate());
        return picDirectoryMapper.insertPicDirectory(picDirectory);
    }

    /**
     * 修改目录列
     * 
     * @param picDirectory 目录列
     * @return 结果
     */
    @Override
    public int updatePicDirectory(PicDirectory picDirectory)
    {
        picDirectory.setUpdateTime(DateUtils.getNowDate());
        return picDirectoryMapper.updatePicDirectory(picDirectory);
    }

    /**
     * 批量删除目录列
     * 
     * @param ids 需要删除的目录列主键
     * @return 结果
     */
    @Override
    public int deletePicDirectoryByIds(Long[] ids)
    {
        return picDirectoryMapper.deletePicDirectoryByIds(ids);
    }

    /**
     * 删除目录列信息
     * 
     * @param id 目录列主键
     * @return 结果
     */
    @Override
    public int deletePicDirectoryById(Long id)
    {
        return picDirectoryMapper.deletePicDirectoryById(id);
    }

    @Override
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryApiList(Long directoryId) {
//        List<PicDirectory> list = picDirectoryMapper.selectPicDirectoryIdList(directoryId);
//        return PageUtil.convertPageInfo2PageDTO(list, picDirectoryConvert::bo2Dto);
        PageInfo<PicDirectoryListDTO> pageInfo = new PageInfo<>();
        pageInfo.setList(picDirectoryMapper.selectPicDirectoryIdList(directoryId));
        return pageInfo;
    }

    @Override
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryNowList() {

//        List<PicDirectory> list = picDirectoryMapper.selectPicDirectoryNowList();
//        return PageUtil.convertPageInfo2PageDTO(list,picDirectoryConvert::bo2Dto);

        PageInfo<PicDirectoryListDTO> pageInfo = new PageInfo<>();
        pageInfo.setList(picDirectoryMapper.selectPicDirectoryNowList());
        return pageInfo;
    }

    @Override
    public PageInfo<PicDirectoryListDTO> selectPicDirectoryHostList() {
//        List<PicDirectory> list = picDirectoryMapper.selectPicDirectoryHostList();
//        return PageUtil.convertPageInfo2PageDTO(list,picDirectoryConvert::bo2Dto);

        PageInfo<PicDirectoryListDTO> pageInfo = new PageInfo<>();
        pageInfo.setList(picDirectoryMapper.selectPicDirectoryHostList());
        return pageInfo;
    }

    @Override
    public PageInfo<PicDirectoryListDTO> selectApiList(Long directoryId) {
        // 查询二级目录
        PageInfo<PicDirectoryListDTO> pageInfo = new PageInfo<>();
        List<PicDirectoryListDTO> list = picDirectoryMapper.selectPicDirectoryIdList(directoryId);
        pageInfo.setList(list);

        // 查询三级目录图片列表
        list.stream().forEach(listDTO -> {
            // 查询三级目录
            listDTO.setList(picDirectoryMapper.selectPicDirectoryIdList(listDTO.getId()));
        });

        return pageInfo;
    }
}
