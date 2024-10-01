package com.ruoyi.biz.api.config;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信参数配置
 */
@Component
public class WeChatConfig {

    public static final String ACCESS_TOKEN_KEY="wechat:accessToken";

    /**
     * 小程序ID
     */
    public static  String appId;

    /**
     * 小程序密钥
     */
    public static  String appSecret;


    private static RedisCache redisCache;



    public static String getAppId() {
        return appId;
    }

    @Value("${wechat.appId}")
    public  void setAppId(String appId) {
        WeChatConfig.appId = appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    @Value("${wechat.appSecret}")
    public  void setAppSecret(String appSecret) {
        WeChatConfig.appSecret = appSecret;
    }

    public static RedisCache getRedisCache() {
        return redisCache;
    }

    @Autowired
    public  void setRedisCache(RedisCache redisCache) {
        WeChatConfig.redisCache = redisCache;
    }

    public static String getAuthUrl(){
        return "https://api.weixin.qq.com/sns/jscode2session?appid=" + getAppId() + "&secret=" + getAppSecret() + "&grant_type=authorization_code";
    }

    public static  String getAccessToken(){
        String accessToken = redisCache.getCacheObject(ACCESS_TOKEN_KEY);
        if (StringUtils.isBlank(accessToken)){
            accessToken = refreshAccessToken();
        }
        return accessToken;
    }

    public static synchronized String refreshAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + getAppId() + "&secret="+getAppSecret();
        String result = HttpUtil.get(url);
        String accessToken = "";
        Integer expiresIn = 0;
        try {
            accessToken = JSONObject.parseObject(result).get("access_token").toString();
            expiresIn = Integer.valueOf(JSONObject.parseObject(result).get("expires_in").toString());
            redisCache.setCacheObject(ACCESS_TOKEN_KEY,accessToken,expiresIn, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return accessToken;
    }

    /**
     * 小程序获取手机号
     * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
     */
    public static String getPhoneUrl() {
        return "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + getAccessToken();
    }

    /**
     * 参数以raw的方式做post请求
     *
     * @param url
     * @param stringJson
     * @param headers
     * @param encode
     * @return
     */
    public static String httpPostRaw(String url, String stringJson, Map<String, String> headers, String encode) {
        String str = "";
        if (encode == null) {
            encode = "utf-8";
        }
        // HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        // 设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            // 响应信息
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            str = content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try { // 关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取小程序码的请求地址
     *
     * @return
     */
    public static String getUnlimitedQRCodeUrl() {
        return "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + getAccessToken();
    }

    /**
     * 获取创建客户小程序码的请求地址
     *
     * @return
     */
    public static String getConnectionQRCodeUrl() {
        return "https://api.weixin.qq.com/subPackages/client/clientInfoTwo/clientInfoTwo?access_token=" + getAccessToken();
    }

    /**
     * 获取微信小程序短链请求
     *
     * @return
     */
    public static String getGenerateShortLinkUrl() {
        return "https://api.weixin.qq.com/wxa/genwxashortlink?access_token=" + getAccessToken();
    }

    /**
     * 获取微信小程序长链请求
     *
     * @return
     */
    public static String getGenerateUrlLinkUrl() {
        return "https://api.weixin.qq.com/wxa/generate_urllink?access_token=" + getAccessToken();
    }

    @PostConstruct
    public void init() {
        WeChatConfig.refreshAccessToken();
    }
}
