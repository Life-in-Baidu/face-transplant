package com.ruoyi.biz.api.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.api.config.WeChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SecurityCheckUtil
 * @Author Iven
 * @Description TODO
 * @Date 2021/3/15 16:51
 */
@Component
@Slf4j
public class SecurityCheckUtil {

    public static  boolean secCheck;

    public static boolean isSecCheck() {
        return secCheck;
    }

    @Value("${wechat.secCheck.enable}")
    public  void setSecCheck(boolean enable) {
        SecurityCheckUtil.secCheck = enable;
    }

    /**
     * 检测图片是否违法违规
     * @param imageUrl
     * @return true 通过
     */
    public static boolean imageCheck(String imageUrl){
        //如果不开启检测  则直接默认通过
        if (!isSecCheck()){
            return  true;
        }
        String url = "https://api.weixin.qq.com/wxa/img_sec_check?access_token="+ WeChatConfig.getAccessToken();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("media",imageUrl);
        String result = HttpUtil.post(url,param);
        log.debug("image check,url:{},result:{}", imageUrl, result);
        String errcode = "";
        try {
            errcode = JSONObject.parseObject(result).get("errcode").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        if ("".equals(errcode)){
            //考虑可能accessToken过期了，手动重新刷新一下
            WeChatConfig.refreshAccessToken();
        }
        if("87014".equals(errcode)){
            //检测到违法内容
            return false;
        }
        return true;

    }

    /**
     * 检测文本是否违法违规
     * @param content
     * @return true 通过
     */
    public static boolean textCheck(String content){
        //如果不开启检测  则直接默认通过
        if (!isSecCheck()){
            return  true;
        }
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+ WeChatConfig.getAccessToken();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("content",content);
        String result = HttpUtil.post(url, JSON.toJSONString(param));
        log.debug("text check,url:{},result:{}", content, result);
        String errcode = "";
        try {
            errcode = JSONObject.parseObject(result).get("errcode").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        if ("".equals(errcode)){
            //考虑可能accessToken过期了，手动重新刷新一下
            WeChatConfig.refreshAccessToken();
        }
        if("87014".equals(errcode)){
            //检测到违法内容
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        System.out.println(textCheck("特3456书yuuo莞6543李zxcz蒜7782法fgnv级"));
    }
}
