package com.ruoyi.biz.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;

/**
 * 阿里云短信服务
 */
@Configuration
public class SendMsgUtil implements Serializable {
    public static String regionId;
    public static String accessKeyId;
    public static String secretKey ;
    public static String signName ;
    public static String templateCode ;
    /*@Value("${aliyun.regionId}")
    public void setRegionId(String regionId) {
        SendMsgUtil.regionId = regionId;
    }
    @Value("${aliyun.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        SendMsgUtil.accessKeyId = accessKeyId;
    }
    @Value("${aliyun.secretKey}")
    public void setSecretKey(String secretKey) {
        SendMsgUtil.secretKey = secretKey;
    }
    @Value("${aliyun.signName}")
    public void setSignName(String signName) {
        SendMsgUtil.signName = signName;
    }
    @Value("${aliyun.templateCode}")
    public void setTemplateCode(String templateCode) {
        SendMsgUtil.templateCode = templateCode;
    }*/

    public static IAcsClient client;
    public static  CommonRequest request;

    @PostConstruct
    public void InitObject(){
        DefaultProfile profile = DefaultProfile.getProfile(SendMsgUtil.regionId, SendMsgUtil.accessKeyId, SendMsgUtil.secretKey);
        client = new DefaultAcsClient(profile);
        request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("SignName", SendMsgUtil.signName);
        request.putQueryParameter("TemplateCode", SendMsgUtil.templateCode);
    }


    /**
     * @param param 参数
     * @param phone 手机号
     * @return 是否发送
     */
    public static boolean sendMsg(Map<String, Object> param, String phone) {
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
             return false;
        }
        return true;
    }


}
