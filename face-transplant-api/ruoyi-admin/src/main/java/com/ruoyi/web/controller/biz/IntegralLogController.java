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
import com.ruoyi.biz.domain.IntegralLog;
import com.ruoyi.biz.service.IIntegralLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 积分变动日志Controller
 * 
 * @author hzw
 * @date 2024-08-10
 */
@RestController
@RequestMapping("/biz/log")
public class IntegralLogController extends BaseController
{
    @Autowired
    private IIntegralLogService integralLogService;

    /**
     * 查询积分变动日志列表
     */
    @PreAuthorize("@ss.hasPermi('biz:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(IntegralLog integralLog)
    {
        startPage();
        List<IntegralLog> list = integralLogService.selectIntegralLogList(integralLog);
        return getDataTable(list);
    }

    /**
     * 导出积分变动日志列表
     */
    @PreAuthorize("@ss.hasPermi('biz:log:export')")
    @Log(title = "积分变动日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IntegralLog integralLog)
    {
        List<IntegralLog> list = integralLogService.selectIntegralLogList(integralLog);
        ExcelUtil<IntegralLog> util = new ExcelUtil<IntegralLog>(IntegralLog.class);
        util.exportExcel(response, list, "积分变动日志数据");
    }

    /**
     * 获取积分变动日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(integralLogService.selectIntegralLogById(id));
    }

    /**
     * 新增积分变动日志
     */
    @PreAuthorize("@ss.hasPermi('biz:log:add')")
    @Log(title = "积分变动日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IntegralLog integralLog)
    {
        return toAjax(integralLogService.insertIntegralLog(integralLog));
    }

    /**
     * 修改积分变动日志
     */
    @PreAuthorize("@ss.hasPermi('biz:log:edit')")
    @Log(title = "积分变动日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IntegralLog integralLog)
    {
        return toAjax(integralLogService.updateIntegralLog(integralLog));
    }

    /**
     * 删除积分变动日志
     */
    @PreAuthorize("@ss.hasPermi('biz:log:remove')")
    @Log(title = "积分变动日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(integralLogService.deleteIntegralLogByIds(ids));
    }
}
