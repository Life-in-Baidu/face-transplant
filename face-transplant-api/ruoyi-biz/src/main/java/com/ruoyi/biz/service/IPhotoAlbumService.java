package com.ruoyi.biz.service;

import java.util.List;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.dto.PhotoAlbumListDTO;
import com.ruoyi.biz.param.DirectoryParam;

/**
 * 相册Service接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface IPhotoAlbumService 
{
    /**
     * 查询相册
     * 
     * @param id 相册主键
     * @return 相册
     */
    public PhotoAlbum selectPhotoAlbumById(Long id);

    /**
     * 查询相册列表
     * 
     * @param photoAlbum 相册
     * @return 相册集合
     */
    public List<PhotoAlbum> selectPhotoAlbumList(PhotoAlbum photoAlbum);

    /**
     * 新增相册
     * 
     * @param photoAlbum 相册
     * @return 结果
     */
    public int insertPhotoAlbum(PhotoAlbum photoAlbum);

    /**
     * 修改相册
     * 
     * @param photoAlbum 相册
     * @return 结果
     */
    public int updatePhotoAlbum(PhotoAlbum photoAlbum);

    /**
     * 批量删除相册
     * 
     * @param ids 需要删除的相册主键集合
     * @return 结果
     */
    public int deletePhotoAlbumByIds(Long[] ids);

    /**
     * 删除相册信息
     * 
     * @param id 相册主键
     * @return 结果
     */
    public int deletePhotoAlbumById(Long id);


    /**
     * 根据目录查询模板胖瘦,随机获取9张
     * @return 相册集合
     */
    public List<PhotoAlbumListDTO> selectPhotoAlbumIdList(DirectoryParam param) throws Exception;

    /**
     * 获取随机图片
     * @return
     */
    public String getUrl();
}
