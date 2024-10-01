package com.ruoyi.pay.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 网络请求工具类
 */

@Slf4j
public class HttpUtil {
    /**
     * 携带证书的httpPOST请求
     *
     * @param postDataXML
     * @param certPassword
     * @param requestUrl
     * @param certFilePath
     * @return
     * @throws Exception
     */
    public static String HttpClientSSL(String postDataXML, String certPassword, String requestUrl, String certFilePath) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            KeyStore keyStore = getCertificate(certPassword, certFilePath);
            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
            SSLConnectionSocketFactory sslf = new SSLConnectionSocketFactory(sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(sslf).build();
            HttpPost httpPost = new HttpPost(requestUrl);//退款接口
            httpPost.addHeader("Content-Type", "text/xml");
            StringEntity reqEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.setEntity(reqEntity);
            String result = null;
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF8");
            EntityUtils.consume(httpEntity);
            return result;
        } finally {//关流
            httpClient.close();
            httpResponse.close();
        }
    }

    /**
     * 获取证书文件
     *
     * @certPassword 证书密码
     * @fileUrl 文件路径
     */
    private static KeyStore getCertificate(String certPassword, String fileUrl) {
        //try-with-resources 关流
        try (FileInputStream inputStream = new FileInputStream(new File(fileUrl))) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, certPassword.toCharArray());
            return keyStore;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
