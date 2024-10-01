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
import com.ruoyi.biz.domain.BizLbt;
import com.ruoyi.biz.service.IBizLbtService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图Controller
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@RestController
@RequestMapping("/biz/lbt")
public class BizLbtController extends BaseController
{
    @Autowired
    private IBizLbtService bizLbtService;

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizLbt bizLbt)
    {
        startPage();
        List<BizLbt> list = bizLbtService.selectBizLbtList(bizLbt);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizLbt bizLbt)
    {
        List<BizLbt> list = bizLbtService.selectBizLbtList(bizLbt);
        ExcelUtil<BizLbt> util = new ExcelUtil<BizLbt>(BizLbt.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bizLbtService.selectBizLbtById(id));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizLbt bizLbt)
    {
        return toAjax(bizLbtService.insertBizLbt(bizLbt));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizLbt bizLbt)
    {
        return toAjax(bizLbtService.updateBizLbt(bizLbt));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('biz:lbt:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizLbtService.deleteBizLbtByIds(ids));
    }
}
