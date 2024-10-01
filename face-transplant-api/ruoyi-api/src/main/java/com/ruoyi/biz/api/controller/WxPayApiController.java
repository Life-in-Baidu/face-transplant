package com.ruoyi.biz.api.controller;

import com.ruoyi.biz.service.WechatPayNotifyService;
import com.ruoyi.common.core.domain.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信支付Controller
 *
 * @author lian.tian
 * @date 2022-01-06
 */
@RestController
@RequestMapping("/api/weChatPay")
@Slf4j
public class WxPayApiController {

    @Autowired
    private WechatPayNotifyService wechatPayNotifyService;

    @PostMapping("/wechat/notify")
    public ApiResult<Map<String, String>> notify(HttpServletRequest request, HttpServletResponse response) {
        return ApiResult.success(wechatPayNotifyService.handleNotify(request, response));
    }
}
