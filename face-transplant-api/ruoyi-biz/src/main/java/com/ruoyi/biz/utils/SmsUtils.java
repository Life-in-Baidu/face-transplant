package com.ruoyi.biz.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SmsUtils
 * @Author Iven
 * @Description TODO
 * @Date 2020/10/20 11:05
 */
public class SmsUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(SmsUtils.class);
    private static final String appKey = "";
    private static final String secret = "";

    //极光短信
    public static void sendTemplateSms(String mobile, String code){
        SMSClient client = new SMSClient(secret, appKey);
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("code",code);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(mobile)
                .setTempId(1)
                .setTempPara(paramMap)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            System.out.println(res.toString());
            LOG.info(res.toString());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
            //sendTemplateSms();
    }

}
