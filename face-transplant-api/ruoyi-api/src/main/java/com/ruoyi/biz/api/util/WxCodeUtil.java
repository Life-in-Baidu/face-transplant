package com.ruoyi.biz.api.util;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.biz.api.config.WeChatConfig;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpMethod;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xph
 * @ClassName WxCodeUtil
 * @Description 小程序码工具类
 * @date 2023/6/5
 */

public class WxCodeUtil {

    private final static String SCENE = "scene";
    private final static String IS_HYALINE = "is_hyaline";
    private final static String AUTO_COLOR = "auto_color";
    private final static String PAGE = "page";
    private final static String CHECK_PATH = "check_path";

    /**
     * 连接参数
     */
    private final static Integer CONNECT_TIMEOUT = 10000;
    private final static Integer READ_TIMEOUT = 10000;


    /**
     * 合同详情页
     */
    private final static String AGREEMENT_PAGE_VALUE_DETAIL = "subPackages/contract/contractDetail/contractDetail";
    private final static String AGREEMENT_PAGE_QUERY_PARAM = "id=";
    private final static String AGREEMENT_PAGE_CUSTOMER_TYPE = "customerType=";
    private final static String CONNECTION_PAGE_VALUE_DETAIL = "pages/generate/generate";

    /**
     * 小程序登录页
     */
    private final static String LOGIN_PAGE_QUERY_PARAM = "uuid=";

    private static String joinCreateCodeInfo(String prefix, Object id) {
        return prefix + id;
    }


    /**
     * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析
     */
    private static final String BASE64_IMAGE = "data:image/png;base64,%s";

    /**
     * 获取小程序码创建的连接
     *
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getLimitCodeConnection() throws IOException {
        String wxCodeURL = WeChatConfig.getUnlimitedQRCodeUrl();
        URL url = new URL(wxCodeURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 提交模式
        httpURLConnection.setRequestMethod(HttpMethod.POST.name());
        //连接超时 单位毫秒
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
        //读取超时 单位毫秒
        httpURLConnection.setReadTimeout(READ_TIMEOUT);
        // 发送POST请求必须设置如下两行
        httpURLConnection.setDoOutput(Boolean.TRUE);
        httpURLConnection.setDoInput(Boolean.TRUE);
        return httpURLConnection;
    }

    /**
     * 输入流的数据转换成 base64
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String convertImageToBase64(BufferedInputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeBase64String(imageBytes);
    }

    /**
     * 获取合同小程序码转换成 base64
     *
     * @param agreementId
     */
    public static String getAgreementCode2Base64(Long agreementId) throws IOException {
        BufferedInputStream bis = null;
        try {
            HttpURLConnection httpURLConnection = getLimitCodeConnection();
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put(SCENE, joinCreateCodeInfo(AGREEMENT_PAGE_QUERY_PARAM, agreementId));
            paramJson.put(IS_HYALINE, Boolean.TRUE);
            paramJson.put(AUTO_COLOR, Boolean.TRUE);
            paramJson.put(PAGE, AGREEMENT_PAGE_VALUE_DETAIL);
            paramJson.put(CHECK_PATH, Boolean.FALSE);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            return String.format(BASE64_IMAGE, convertImageToBase64(bis));
        } finally {
            bis.close();
        }
    }


    /**
     * 获取客户小程序码转换成 base64
     */
    public static String getConnectionCode2Base64(String uuid) throws IOException {
        BufferedInputStream bis = null;
        try {
            HttpURLConnection httpURLConnection = getLimitCodeConnection();
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put(SCENE, joinCreateCodeInfo(LOGIN_PAGE_QUERY_PARAM, uuid));
            paramJson.put(IS_HYALINE, Boolean.FALSE); //是否透明
            paramJson.put(AUTO_COLOR, Boolean.TRUE); //自动设置颜色
            paramJson.put(PAGE, CONNECTION_PAGE_VALUE_DETAIL);//小程序页面
            paramJson.put(CHECK_PATH, Boolean.TRUE);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            bis = new BufferedInputStream(httpURLConnection.getInputStream());
            return String.format(BASE64_IMAGE, convertImageToBase64(bis));
        } finally {
            bis.close();
        }
    }

}
