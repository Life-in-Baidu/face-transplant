package com.ruoyi.biz.api.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.dto.WxDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/***
 * @author 周周
 * @description WxUtils 微信工具类
 */
@Configuration
@Slf4j
public class WxUtils implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String appId;
    public static String appSecret;
    public static final String APP_ID = "app_id";
    public static final String APP_SECRET = "app_secret";
    public static final String JS_CODE = "js_code";
    public static final String OPENID = "openid";
    public static final String SESSION_KEY = "session_key";
    public static final String AVATAR_URL = "avatarUrl";
    public static final String NICK_NAME = "nickName";
    public static final String AccessToken = "access_token";
    public static final String JSApiTicket = "ticket";

    @Value("${wechat.appId}")
    public void setAppId(String appId) { WxUtils.appId = appId; }
    @Value("${wechat.appSecret}")
    public void setAppSecret(String appSecret) { WxUtils.appSecret = appSecret; }
    public static String getAppId() { return appId; }
    public static String getAppSecret() { return appSecret; }

    public static String getAccessTokenUrl() {
        return "https://api.weixin.qq.com/sns/jscode2session?appid={app_id}&secret={app_secret}&js_code={js_code}&grant_type=authorization_code";
    }
    /***
     * 获取access_token,临时凭证
     */
    public static String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WxUtils.appId+ "&secret=" +WxUtils.appSecret+ "" ;
        String backData = HttpUtil.get(url);
        return JSONObject.parseObject(backData).getString(AccessToken);
    }

    /**
     * 获取微信认证信息
     * @param code code
     * @return 微信认证信息
     */
    public static JSONObject getAuthInfo(String code){
        Map<String, Object> context = new HashMap<>();
        context.put(APP_ID, WxUtils.getAppId());
        context.put(APP_SECRET, WxUtils.getAppSecret());
        context.put(JS_CODE, code);
        String accessTokenUrl = StrUtil.format(WxUtils.getAccessTokenUrl(),context);
        return JSONObject.parseObject(HttpUtil.get(accessTokenUrl));
    }

    /**
     * 微信小程序
     * @param encryptedData 加密数据，用户敏感信息
     * @param iv 加密初始化向量
     * @param sessionKey sessionKey
     * @return  用户信息
     */
    public static JSONObject getWxUserInfo(String encryptedData, String iv, String sessionKey) {
        return JSONObject.parseObject(AesCbcUtil.decrypt(encryptedData, sessionKey, iv));
    }

    /**
     * 微信公众号
     *  根据  openId 和 accessToken 获取用户信息
     * @param accessToken accessToken
     * @param openId openId
     * @return 用户信息
     */
    public static JSONObject getUserInfo(String accessToken,String openId){
        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", accessToken, openId);
        String object = HttpUtil.get(url);
        return JSON.parseObject(object);
    }


    /***
     * 获取jsapiTicket
     */
    public static String getJSApiTicket(){
        String accessToken= WxUtils.getAccessToken();
        String urlStr =  "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" +accessToken+ "&type=jsapi" ;
        String backData = HttpUtil.get(urlStr);
        return JSONObject.parseObject(backData).getString(JSApiTicket);
    }

    /**
     * @return 生成随机字符串
     */
    public static String randomString(){
        return UUID.randomUUID().toString();
    }

    /**
     * @return 获取当前时间戳
     */
    public static String currentTime() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * @date： 2015年12月17日 上午9:24:43
     * @description： SHA、SHA1加密
     * @parameter：   str：待加密字符串
     * @return：  加密串
     **/
    public static String SHA1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexStr = new StringBuilder();
            for (byte b : messageDigest) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) { hexStr.append(0); }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param noncestr 随机字符
     * @param timestamp 时间戳
     * @return 获取签名signature
     */
    public static String signature (String noncestr, String timestamp){
        String url="http://mp.weixin.qq.com";
        String str = "jsapi_ticket=" + getJSApiTicket() + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        return SHA1(str);
    }


    /**
     * @return 获取微信跳转通用对象
     */
    public static WxDTO getWxDTO(){
        String noncestr = randomString();
        String timestamp = currentTime();
        String signature = signature(noncestr, timestamp);
        return WxDTO.builder()
                 .noncestr(noncestr)
                 .timestamp(timestamp)
                 .signature(signature)
                 .build();
    }

}
