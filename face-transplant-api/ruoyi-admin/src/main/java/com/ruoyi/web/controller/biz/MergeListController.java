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
import com.ruoyi.biz.domain.MergeList;
import com.ruoyi.biz.service.IMergeListService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 融合组Controller
 * 
 * @author ruoyi
 * @date 2024-05-06
 */
@RestController
@RequestMapping("/biz/list")
public class MergeListController extends BaseController
{
    @Autowired
    private IMergeListService mergeListService;

    /**
     * 查询融合组列表
     */
    @PreAuthorize("@ss.hasPermi('biz:list:list')")
    @GetMapping("/list")
    public TableDataInfo list(MergeList mergeList)
    {
        startPage();
        List<MergeList> list = mergeListService.selectMergeListList(mergeList);
        return getDataTable(list);
    }

    /**
     * 导出融合组列表
     */
    @PreAuthorize("@ss.hasPermi('biz:list:export')")
    @Log(title = "融合组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MergeList mergeList)
    {
        List<MergeList> list = mergeListService.selectMergeListList(mergeList);
        ExcelUtil<MergeList> util = new ExcelUtil<MergeList>(MergeList.class);
        util.exportExcel(response, list, "融合组数据");
    }

    /**
     * 获取融合组详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:list:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mergeListService.selectMergeListById(id));
    }

    /**
     * 新增融合组
     */
    @PreAuthorize("@ss.hasPermi('biz:list:add')")
    @Log(title = "融合组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MergeList mergeList)
    {
        return toAjax(mergeListService.insertMergeList(mergeList));
    }

    /**
     * 修改融合组
     */
    @PreAuthorize("@ss.hasPermi('biz:list:edit')")
    @Log(title = "融合组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MergeList mergeList)
    {
        return toAjax(mergeListService.updateMergeList(mergeList));
    }

    /**
     * 删除融合组
     */
    @PreAuthorize("@ss.hasPermi('biz:list:remove')")
    @Log(title = "融合组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mergeListService.deleteMergeListByIds(ids));
    }
}
