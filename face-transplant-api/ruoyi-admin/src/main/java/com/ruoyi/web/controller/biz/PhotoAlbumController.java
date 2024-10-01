package com.ruoyi.web.controller.biz;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.biz.utils.FaceFusionUtils;
import com.tencentcloudapi.ft.v20200304.models.FaceRect;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.biz.domain.PhotoAlbum;
import com.ruoyi.biz.service.IPhotoAlbumService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 相册Controller
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@RestController
@RequestMapping("/biz/album")
public class PhotoAlbumController extends BaseController
{
    @Autowired
    private IPhotoAlbumService photoAlbumService;

    /**
     * 查询相册列表
     */
    @PreAuthorize("@ss.hasPermi('biz:album:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhotoAlbum photoAlbum)
    {
        startPage();
        List<PhotoAlbum> list = photoAlbumService.selectPhotoAlbumList(photoAlbum);
        return getDataTable(list);
    }

    /**
     * 导出相册列表
     */
    @PreAuthorize("@ss.hasPermi('biz:album:export')")
    @Log(title = "相册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhotoAlbum photoAlbum)
    {
        List<PhotoAlbum> list = photoAlbumService.selectPhotoAlbumList(photoAlbum);
        ExcelUtil<PhotoAlbum> util = new ExcelUtil<PhotoAlbum>(PhotoAlbum.class);
        util.exportExcel(response, list, "相册数据");
    }

    /**
     * 获取相册详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:album:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(photoAlbumService.selectPhotoAlbumById(id));
    }

    /**
     * 新增相册
     */
    @PreAuthorize("@ss.hasPermi('biz:album:add')")
    @Log(title = "相册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhotoAlbum photoAlbum)
    {
        if (ObjectUtil.isEmpty(photoAlbum.getImgType())) {
            photoAlbum.setImgType(1); // 默认基础图片
        }
        return toAjax(photoAlbumService.insertPhotoAlbum(photoAlbum));
    }

    /**
     * 修改相册
     */
    @PreAuthorize("@ss.hasPermi('biz:album:edit')")
    @Log(title = "相册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhotoAlbum photoAlbum)
    {
        return toAjax(photoAlbumService.updatePhotoAlbum(photoAlbum));
    }

    /**
     * 删除相册
     */
    @PreAuthorize("@ss.hasPermi('biz:album:remove')")
    @Log(title = "相册", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(photoAlbumService.deletePhotoAlbumByIds(ids));
    }
}
