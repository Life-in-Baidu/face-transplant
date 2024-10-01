package com.ruoyi.web.controller.biz;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.biz.domain.MergeImg;
import com.ruoyi.biz.service.IMergeImgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 融合图片Controller
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@RestController
@RequestMapping("/biz/mergeImg")
public class MergeImgController extends BaseController
{
    @Autowired
    private IMergeImgService mergeImgService;

    /**
     * 查询融合图片列表
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:list')")
    @GetMapping("/list")
    public TableDataInfo list(MergeImg mergeImg)
    {
        startPage();
        List<MergeImg> list = mergeImgService.selectMergeImgList(mergeImg);
        return getDataTable(list);
    }

    /**
     * 导出融合图片列表
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:export')")
    @Log(title = "融合图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MergeImg mergeImg)
    {
        List<MergeImg> list = mergeImgService.selectMergeImgList(mergeImg);
        ExcelUtil<MergeImg> util = new ExcelUtil<MergeImg>(MergeImg.class);
        util.exportExcel(response, list, "融合图片数据");
    }

    /**
     * 获取融合图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mergeImgService.selectMergeImgById(id));
    }

    /**
     * 新增融合图片
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:add')")
    @Log(title = "融合图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MergeImg mergeImg)
    {
        return toAjax(mergeImgService.insertMergeImg(mergeImg));
    }

    /**
     * 修改融合图片
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:edit')")
    @Log(title = "融合图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MergeImg mergeImg)
    {
        return toAjax(mergeImgService.updateMergeImg(mergeImg));
    }

    /**
     * 删除融合图片
     */
    @PreAuthorize("@ss.hasPermi('biz:mergeImg:remove')")
    @Log(title = "融合图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mergeImgService.deleteMergeImgByIds(ids));
    }
}
