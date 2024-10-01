package com.ruoyi.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.pay.config.WeChatPayConfig;
import com.ruoyi.pay.param.PayParam;
import com.ruoyi.pay.param.TransfersParam;
import com.ruoyi.pay.service.WeChatPayService;
import com.ruoyi.pay.utils.*;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.*;

@Service
@Slf4j
public class WeChatPayServiceImpl implements WeChatPayService {
    private static final String APPID = "appid";
    private static final String MCH_ID = "mchid";
    private static final String NOTIFY_URL = "notify_url";
    private static final String TRADE_TYPE = "trade_type";
    private static final String DESCRIPTION = "description";
    private static final String PAYER = "payer";
    private static final String TOTAL = "total";
    private static final String CURRENCY = "currency";
    private static final String OPENID = "openid";
    private static final String MCH_APPID = "mch_appid";
    private static final String AMOUNT = "amount";
    private static final String SIGN = "sign";
    private static final String NONCE_STR = "nonce_str";
    private static final String PARTNER_TRADE_NO = "partner_trade_no";
    private static final String CHECK_NAME = "check_name";
    private static final String DESC = "desc";
    private static final String RESULT_CODE = "result_code";
    private static final String SUCCESS = "SUCCESS";
    private static final String NOTPAY = "NOTPAY";
    private static final String CLOSED = "CLOSED";

    private static final String UTF_8 = "UTF-8";
    private static final String TRADE_STATE = "trade_state";
    private static final String RETURN_CODE = "return_code";
    private static final String RETURN_MSG = "return_msg";
    private static final int RANDOM_LENGTH = 30;

    @Override
    public JSONObject pay(PayParam payParam) {
        try {
            String prepay_id = null;
            HttpClient httpClient = initHttpClient().build();
            HttpPost httpPost = new HttpPost(WeChatPayConfig.PAY_URL);

            // 请求body参数
            JSONObject reqdata = new JSONObject();
            // 订单金额信息
            JSONObject amount = new JSONObject();
            // TODO 临时设置订单金额为0.01
            amount.put(TOTAL, payParam.getPrice());
//            amount.put(TOTAL, 1);
            amount.put(CURRENCY, WeChatPayConfig.Currency.CNY);
            // 支付者信息
            JSONObject payer = new JSONObject();
            payer.put(OPENID, payParam.getOpenId());
            // 订单金额
            reqdata.put(AMOUNT, amount);
            // 商户号
            reqdata.put(MCH_ID, WeChatPayConfig.getMchId());
            // 小程序ID
            reqdata.put(APPID, WeChatPayConfig.getAppId());
            // 商品描述
            reqdata.put(DESCRIPTION, payParam.getDescription());
            // 异步通知地址
            reqdata.put(NOTIFY_URL, WeChatPayConfig.getNotifyUrl());
            reqdata.put(PAYER, payer);
            // 商户订单号
            reqdata.put(WeChatPayConfig.OUT_TRADE_NO, payParam.getOrderNo());

            StringEntity entity = new StringEntity(JSONObject.toJSONString(reqdata), UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");

            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost);
            log.info("response: {}", response);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("statusCode: {}", statusCode);
            if (statusCode == 200) {
                JSONObject payRes = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                prepay_id = payRes.get("prepay_id").toString();
                log.info("success,return body: {}", EntityUtils.toString(response.getEntity()));
            } else if (statusCode == 204) {
                log.info("success");
            } else {
                log.error("failed,resp code: {}, return body: {}", statusCode, EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
            log.info("开始生成签名参数");
            return getSignParams(prepay_id);
        } catch (Throwable e) {
            log.error("Create we chat code url failed,orderNo:{}:", payParam.getOrderNo(), e);
            throw new CustomException("Create we chat code url failed", e);
        }
    }

    @Override
    public Map<String, String> transfers(TransfersParam transfersParam) {
        try {
            String certFile = WeChatPayConfig.getCertFilePath();//api证书文件存放路径
            //根据微信支付api来设置
            Map<String, Object> map = new HashMap<>();
            // 商户账号appid
            map.put(MCH_APPID, WeChatPayConfig.getAppId());
            // 商户号
            map.put(MCH_ID, WeChatPayConfig.getMchId());
            // 随机字符串，不长于32位
            map.put(NONCE_STR, getRandomString(RANDOM_LENGTH));
            // 商户订单号
            map.put(PARTNER_TRADE_NO, OrderGenerator.getInstance().create());
            // 用户openid
            map.put(OPENID, transfersParam.getOpenid());
            // 校验用户姓名选项	NO_CHECK：不校验真实姓名, FORCE_CHECK：强校验真实姓名
            map.put(CHECK_NAME, transfersParam.getCheckName());
            // 金额
            map.put(AMOUNT, transfersParam.getAmount());
            // 付款备注，必填。注意：备注中的敏感词会被转成字符*
            map.put(DESC, transfersParam.getDesc());
            // 签名
            map.put(SIGN, getSign(map));
            String request = genXml(map);
            log.info("请求参数：{}", request);
            // 发现xml消息
            String response = HttpUtil.HttpClientSSL(request, WeChatPayConfig.getMchId(), WeChatPayConfig.TRANSFERS_URL, certFile);
            //解析返回的xml数据
            log.info("微信企业付款到零钱返回结果: {}", response);
            Map<String, String> resultMap = WXPayXmlUtil.xmlToMap(response);
            return resultMap;
        } catch (Exception e) {
            log.info("transfers failed, params: {}, e: {}", e, transfersParam);
            throw new CustomException("transfers failed");
        }
    }

    /**
     * 微信支付签名算法sign
     */
    private static String getSign(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        // 获取map中的key转为array
        String[] keyArr = map.keySet().toArray(new String[map.keySet().size()]);
        Arrays.sort(keyArr);// 对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + WeChatPayConfig.KEY);
        return Md5Util.string2MD5(sb.toString());
    }

    //生成的随机字符串
    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(2);
            long result = 0;

            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:

                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }

    private WechatPayHttpClientBuilder initHttpClient() throws UnsupportedEncodingException {
        // 加载平台证书（mchId：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                new WechatPay2Credentials(WeChatPayConfig.getMchId(), new PrivateKeySigner(WeChatPayConfig.getMchSerialNo(), WechatPayUtils.getPrivateKey())), WeChatPayConfig.getApiV3Key().getBytes("utf-8"));
        //请求URL
        return WechatPayHttpClientBuilder.create()
                .withMerchant(WeChatPayConfig.getMchId(), WeChatPayConfig.getMchSerialNo(), WechatPayUtils.getPrivateKey())
                .withValidator(new WechatPay2Validator(verifier));
    }

    /**
     * 小程序调起支付数据组织签名
     *
     * @param prepayId
     * @return
     */
    private JSONObject getSignParams(String prepayId) throws Exception {
        log.info("prepay_id: {}", prepayId);
        String pkgInfo = StringUtils.join("prepay_id=", prepayId);
        // 获取时间戳除以千变字符串
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = OrderGenerator.getInstance().create();
        JSONObject signParams = new JSONObject();
        signParams.put("appId", WeChatPayConfig.getAppId());
        signParams.put("timeStamp", timeStamp);
        signParams.put("nonceStr", nonceStr);
        signParams.put("package", pkgInfo);
        signParams.put("signType", "RSA");
        String content = WeChatPayConfig.getAppId() + "\n"
                + timeStamp + "\n"
                + nonceStr + "\n"
                + pkgInfo + "\n";
        log.info("待签名字符串: {}", content);
        signParams.put("paySign", sign(content, WechatPayUtils.getPrivateKey()));
        return signParams;
    }

    private static String sign(String content, PrivateKey privateKey) throws Exception {
        Assert.notNull(content, "签名内容不能为空");
        final Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    private String genXml(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String k : map.keySet()) {
            Object value = map.get(k);
            sb.append("<" + k + ">" + value + "</" + k + ">");
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("</xml>");
        try {
            // 将数据使用进行编码
            return new String(sb.toString().getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
