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
import com.ruoyi.biz.domain.Qrcode;
import com.ruoyi.biz.service.IQrcodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分享记录Controller
 * 
 * @author hzw
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/biz/qrcode")
public class QrcodeController extends BaseController
{
    @Autowired
    private IQrcodeService qrcodeService;

    /**
     * 查询分享记录列表
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:list')")
    @GetMapping("/list")
    public TableDataInfo list(Qrcode qrcode)
    {
        startPage();
        List<Qrcode> list = qrcodeService.selectQrcodeList(qrcode);
        return getDataTable(list);
    }

    /**
     * 导出分享记录列表
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:export')")
    @Log(title = "分享记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Qrcode qrcode)
    {
        List<Qrcode> list = qrcodeService.selectQrcodeList(qrcode);
        ExcelUtil<Qrcode> util = new ExcelUtil<Qrcode>(Qrcode.class);
        util.exportExcel(response, list, "分享记录数据");
    }

    /**
     * 获取分享记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(qrcodeService.selectQrcodeById(id));
    }

    /**
     * 新增分享记录
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:add')")
    @Log(title = "分享记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Qrcode qrcode)
    {
        return toAjax(qrcodeService.insertQrcode(qrcode));
    }

    /**
     * 修改分享记录
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:edit')")
    @Log(title = "分享记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Qrcode qrcode)
    {
        return toAjax(qrcodeService.updateQrcode(qrcode));
    }

    /**
     * 删除分享记录
     */
    @PreAuthorize("@ss.hasPermi('biz:qrcode:remove')")
    @Log(title = "分享记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qrcodeService.deleteQrcodeByIds(ids));
    }
}
