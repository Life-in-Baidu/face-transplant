package com.ruoyi.biz.controller;

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
import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.service.ISetmealService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 套餐Controller
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
@RestController
@RequestMapping("/biz/setmeal")
public class SetmealController extends BaseController
{
    @Autowired
    private ISetmealService setmealService;

    /**
     * 查询套餐列表
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(Setmeal setmeal)
    {
        startPage();
        List<Setmeal> list = setmealService.selectSetmealList(setmeal);
        return getDataTable(list);
    }

    /**
     * 导出套餐列表
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:export')")
    @Log(title = "套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Setmeal setmeal)
    {
        List<Setmeal> list = setmealService.selectSetmealList(setmeal);
        ExcelUtil<Setmeal> util = new ExcelUtil<Setmeal>(Setmeal.class);
        util.exportExcel(response, list, "套餐数据");
    }

    /**
     * 获取套餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:query')")
    @GetMapping(value = "/{mealId}")
    public AjaxResult getInfo(@PathVariable("mealId") Long mealId)
    {
        return AjaxResult.success(setmealService.selectSetmealByMealId(mealId));
    }

    /**
     * 新增套餐
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:add')")
    @Log(title = "套餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Setmeal setmeal)
    {
        return toAjax(setmealService.insertSetmeal(setmeal));
    }

    /**
     * 修改套餐
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:edit')")
    @Log(title = "套餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Setmeal setmeal)
    {
        return toAjax(setmealService.updateSetmeal(setmeal));
    }

    /**
     * 删除套餐
     */
    @PreAuthorize("@ss.hasPermi('biz:setmeal:remove')")
    @Log(title = "套餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{mealIds}")
    public AjaxResult remove(@PathVariable Long[] mealIds)
    {
        return toAjax(setmealService.deleteSetmealByMealIds(mealIds));
    }
}
