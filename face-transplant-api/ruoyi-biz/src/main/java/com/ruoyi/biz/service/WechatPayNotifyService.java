package com.ruoyi.biz.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface WechatPayNotifyService {
    String TRANSACTION_ID = "transaction_id";
    Integer IS_MEMBER = 1;
    Integer NOT_MEMBER = 0;

    /**
     * 处理微信支付的回调.
     * 参考:https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_7&index=8
     *
     * @param request  微信返回的输入参数
     * @param response
     */
    Map<String, String> handleNotify(HttpServletRequest request, HttpServletResponse response);

}
