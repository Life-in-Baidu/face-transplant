package com.ruoyi.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置.
 */
@Component
public class WeChatPayConfig {

    /**
     * 小程序ID
     */
    public static String appId;

    /**
     * 商户号.
     */
    public static String mchId;

    /**
     * 商户证书序列号
     */
    public static String mchSerialNo;

    /**
     * V3密钥
     */
    private static String apiV3Key;

    /**
     * 微信密钥路径
     */
    public static String wechatKeyPath;

    /**
     * 商户证书路径
     */
    public static String certFilePath;

    /**
     * 回调地址.
     */
    public static String notifyUrl;

    /**
     * 设备号.
     */
    public static final String DEVICE_INFO = "minProgram";

    public static final String OUT_TRADE_NO = "out_trade_no";

    /**
     * 支付请求地址.
     */
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**
     * 查询订单地址.
     */
    public static final String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 关闭订单地址
     */
    public static final String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";

    /** 付款地址 */
    public static final String TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     * 商户的key【API密钥】.
     */
    public static final String KEY = "957seVqZ5Fihd9YMft9QONPPInS8PFXM";

    public enum Currency {
        CNY
    }

    public enum TradeType {
        NATIVE,
        JSAPI,
        APP
    }

    public static String getAppId() {
        return appId;
    }

    @Value("${wechat.appId}")
    public void setAppId(String appId) {
        WeChatPayConfig.appId = appId;
    }

    public static String getMchId() {
        return mchId;
    }

    @Value("${wechat.mchId}")
    public void setMchId(String mchId) {
        WeChatPayConfig.mchId = mchId;
    }

    public static String getMchSerialNo() {
        return mchSerialNo;
    }

    @Value("${wechat.mchSerialNo}")
    public void setMchSerialNo(String mchSerialNo) {
        WeChatPayConfig.mchSerialNo = mchSerialNo;
    }

    public static String getApiV3Key() {
        return apiV3Key;
    }

    @Value("${wechat.apiV3Key}")
    public void setApiV3Key(String apiV3Key) {
        WeChatPayConfig.apiV3Key = apiV3Key;
    }

    public static String getWechatKeyPath() {
        return wechatKeyPath;
    }

    @Value("${wechat.wechatKeyPath}")
    public void setWechatKeyPath(String wechatKeyPath) {
        WeChatPayConfig.wechatKeyPath = wechatKeyPath;
    }

    public static String getCertFilePath() {
        return certFilePath;
    }

    @Value("${wechat.certFilePath}")
    public void setCertFilePath(String certFilePath) {
        WeChatPayConfig.certFilePath = certFilePath;
    }

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    @Value("${wechat.notifyUrl}")
    public void setNotifyUrl(String notifyUrl) {
        WeChatPayConfig.notifyUrl = notifyUrl;
    }
}