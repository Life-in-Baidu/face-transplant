package com.ruoyi.web.controller.biz;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.ruoyi.biz.dto.PicDirectoryListDTO;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.exception.ServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import com.ruoyi.biz.domain.PicDirectory;
import com.ruoyi.biz.service.IPicDirectoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 目录列Controller
 * 
 * @author ruoyi
 * @date 2024-05-05
 */
@RestController
@RequestMapping("/biz/directory")
public class PicDirectoryController extends BaseController
{
    @Autowired
    private IPicDirectoryService picDirectoryService;

    /**
     * 查询目录列列表
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:list')")
    @GetMapping("/list")
    public TableDataInfo list(PicDirectory picDirectory)
    {
        startPage();
        List<PicDirectory> list = picDirectoryService.selectPicDirectoryList(picDirectory);
        return getDataTable(list);
    }


    /**
     * 查询目录列列表
     */
    @GetMapping("/getList/{directoryId}")
    public TableDataInfo getDirectorylist(
            @ApiParam(name = "directoryId", value = "目录id-首页一级目录传0", required = true,defaultValue = "0")
            @PathVariable("directoryId") Long directoryId)
    {
        startPage();
        PageInfo<PicDirectoryListDTO> picDirectoryPageInfo =
                picDirectoryService.selectPicDirectoryApiList(directoryId);
        return getDataTable(picDirectoryPageInfo.getList());
    }

    /**
     * 导出目录列列表
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:export')")
    @Log(title = "目录列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PicDirectory picDirectory)
    {
        List<PicDirectory> list = picDirectoryService.selectPicDirectoryList(picDirectory);
        ExcelUtil<PicDirectory> util = new ExcelUtil<PicDirectory>(PicDirectory.class);
        util.exportExcel(response, list, "目录列数据");
    }

    /**
     * 获取目录列详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(picDirectoryService.selectPicDirectoryById(id));
    }

    /**
     * 新增目录列
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:add')")
    @Log(title = "目录列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PicDirectory picDirectory)
    {
        if (ObjectUtil.isEmpty(picDirectory.getPrice())) {
            BigDecimal price = new BigDecimal(0);
            picDirectory.setPrice(price);
        }
        if (ObjectUtil.isEmpty(picDirectory.getPriceRew())) {
            BigDecimal priceRew = new BigDecimal(0);
            picDirectory.setPriceRew(priceRew);
        }
        if(ObjectUtil.isEmpty(picDirectory.getDirectoryUrl())) {
            throw new ServiceException("上传图片失败，请重新上传");
        }
        return toAjax(picDirectoryService.insertPicDirectory(picDirectory));
    }

    /**
     * 修改目录列
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:edit')")
    @Log(title = "目录列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PicDirectory picDirectory)
    {
        return toAjax(picDirectoryService.updatePicDirectory(picDirectory));
    }

    /**
     * 删除目录列
     */
    @PreAuthorize("@ss.hasPermi('biz:directory:remove')")
    @Log(title = "目录列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(picDirectoryService.deletePicDirectoryByIds(ids));
    }

}
