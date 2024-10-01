package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.param.DirectoryParam;
import org.apache.ibatis.annotations.Param;

/**
 * 相册Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
public interface PhotoAlbumMapper 
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
     * 删除相册
     * 
     * @param id 相册主键
     * @return 结果
     */
    public int deletePhotoAlbumById(Long id);

    /**
     * 批量删除相册
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhotoAlbumByIds(Long[] ids);

    /**
     * 根据目录查询模板胖瘦
     *
     * @param directory_id 目录id
     * @return 相册集合
     */
    public List<PhotoAlbum> selectPhotoAlbumIdList(Long directory_id);

    /**
     * 随机查询对应三级目录的图片/9张
     * @return 相册集合
     */
    public List<PhotoAlbum> selectPhotoAlbumRandList(DirectoryParam param);


    /**
     * 获取随机图片
     * @return
     */
    public String getUrl();

    /**
     * 查询相册
     * @return 相册
     */
    public PhotoAlbum selectPhotoAlbum(PhotoAlbum photoAlbum);
}
