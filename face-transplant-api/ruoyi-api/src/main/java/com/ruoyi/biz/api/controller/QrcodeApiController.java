package com.ruoyi.biz.api.controller;

import com.ruoyi.biz.api.context.ApiContext;
import com.ruoyi.biz.dto.QrcodeDTO;
import com.ruoyi.biz.service.IQrcodeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hzw
 * @date 2024/6/18
 */
@RestController
@RequestMapping("/api/order")
@Api(tags = "积分接口")
public class QrcodeApiController extends BaseController {


    @Autowired
    private IQrcodeService qrcodeService;

    @ApiOperation("当前用户分享统计接口")
    @GetMapping
    public ApiResult<QrcodeDTO> getQrcode() {
        Long userId = ApiContext.getCurrentUserId();
        return ApiResult.success(qrcodeService.getQrcodeNum(userId));
    }

}
