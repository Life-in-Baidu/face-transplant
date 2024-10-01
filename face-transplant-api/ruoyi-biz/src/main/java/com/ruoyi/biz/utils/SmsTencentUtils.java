package com.ruoyi.biz.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;
// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
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
public class SmsTencentUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(SmsTencentUtils.class);
    private static final String secretId = "";
    private static final String secretKey = "";
    private static final String appid = "";
    private static final String sign = "";
    private static final String templateID = "";

    //腾讯短信
    public static void sendTemplateSms(String mobile, String code){
        try {

            Credential cred = new Credential(secretId, secretKey);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            SmsClient client = new SmsClient(cred, "",clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppid(appid);
            req.setSign(sign);
            String senderid = "";
            req.setSenderId(senderid);
            String session = "";
            req.setSessionContext(session);
            req.setTemplateID(templateID);
            String[] phoneNumbers = {"+86"+mobile};
            req.setPhoneNumberSet(phoneNumbers);
            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParams = {code};
            req.setTemplateParamSet(templateParams);
            SendSmsResponse res = client.SendSms(req);
            System.out.println(SendSmsResponse.toJsonString(res));
            System.out.println(res.getRequestId());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
            sendTemplateSms("19176483846","111111");
    }

}
