package com.ruoyi.pay.utils;


import cn.hutool.json.JSONUtil;
import com.ruoyi.pay.config.WeChatPayConfig;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;

/**
 * 微信付款请求工具类
 * @author lian.tian
 */
@Slf4j
public class WechatPayUtils {

    /**
     * 获取私钥。
     *
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey() {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(WeChatPayConfig.getWechatKeyPath())), "utf-8");
        } catch (IOException e) {
            log.error("读取私钥文件移除：{}", e);
            e.printStackTrace();
        }
        String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
        return PemUtil.loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 处理返回对象
     *
     * @param request
     * @return
     */
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * v3 支付异步通知验证签名
     *
     * @param body 异步通知密文
     * @param key  api 密钥
     * @return 异步通知明文
     * @throws Exception 异常信息
     */
    public static String verifyNotify(String body, String key) throws Exception {
        // 获取平台证书序列号
        cn.hutool.json.JSONObject resultObject = JSONUtil.parseObj(body);
        cn.hutool.json.JSONObject resource = resultObject.getJSONObject("resource");
        String cipherText = resource.getStr("ciphertext");
        String nonceStr = resource.getStr("nonce");
        String associatedData = resource.getStr("associated_data");
        AesUtil aesUtil = new AesUtil(key.getBytes(StandardCharsets.UTF_8));
        // 密文解密
        return aesUtil.decryptToString(
                associatedData.getBytes(StandardCharsets.UTF_8),
                nonceStr.getBytes(StandardCharsets.UTF_8),
                cipherText
        );
    }
}
