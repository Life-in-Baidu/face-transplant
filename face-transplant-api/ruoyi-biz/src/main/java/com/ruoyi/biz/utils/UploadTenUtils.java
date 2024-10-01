package com.ruoyi.biz.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;

@Slf4j
@Component
public class UploadTenUtils {

    private static String secretId;
    private static String secretKey;
    private static String bucketName; //指定存储桶名称
    private static String domain;

    public static String getSecretId() {
        return secretId;
    }
    @Value("${tencent.secretId}")
    public void setSecretId(String secretId) {
        UploadTenUtils.secretId = secretId;
    }

    public static String getSecretKey() {
        return secretKey;
    }
    @Value("${tencent.secretKey}")
    public void setSecretKey(String secretKey) {
        UploadTenUtils.secretKey = secretKey;
    }

    public static String getBucketName() {
        return bucketName;
    }
    @Value("${tencent.bucketName}")
    public void setBucketName(String bucketName) {
        UploadTenUtils.bucketName = bucketName;
    }

    public static String getDomain() {
        return domain;
    }
    @Value("${tencent.domain}")
    public void setDomain(String domain) {
        UploadTenUtils.domain = domain;
    }



    /**
     * 上传图片至腾讯存储桶
     * @param localUrl 本地文件路径
     * @param fileHost 主文件夹
     * @param fileName 存储桶文件夹名称
     * @return
     */
    public static String uploadVideo(String localUrl,String fileHost,String fileName){

        // 创建身份认证信息
        COSCredentials cred = new BasicCOSCredentials(UploadTenUtils.getSecretId(), UploadTenUtils.getSecretKey());
        // 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new Region("ap-shanghai");
        // 创建ClientConfig对象
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 创建COS客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的本地文件路径
        File file = new File(localUrl);

        // 构建图片URL
        String key = fileHost + fileName;

        
        try {
            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(UploadTenUtils.getBucketName(), key, file);
            cosClient.putObject(putObjectRequest);
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            cosClient.shutdown();
        }
        // 构建图片URL
        String imageUrl = UploadTenUtils.getDomain() + "/" + key;
        System.out.println("图片已上传成功，访问路径为: " + imageUrl);
        return imageUrl;
    }

    /**
     * 获取存储桶中图片临时路径（代签名路径）
     * @param imageName 图片在存储桶中的路径
     * @return
     */
    public static String getImageUrl(String imageName) {

        //全路径问题，截取图片路径
        int comIndex = imageName.indexOf(".com/");
        if (comIndex != -1) {
            int startIndex = comIndex + 4;
            imageName = imageName.substring(startIndex);
        } else {
            System.out.println("存储桶图片路径问题:" + imageName);
        }


        // 1 初始化用户身份信息(secretId, secretKey)。
        COSCredentials cred = new BasicCOSCredentials(UploadTenUtils.getSecretId(), UploadTenUtils.getSecretKey());
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);

        // 2 生成cos客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 3 创建GeneratePresignedUrlRequest对象并设置参数
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(UploadTenUtils.getBucketName(), imageName);

        // 4 设置过期时间，例如设置URL在一小时后过期
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        request.setExpiration(expiration);

        // 5 生成带签名的URL
        URL signedUrl = cosClient.generatePresignedUrl(request);
        System.out.println("Temporary image path: " + signedUrl.toString());

        // 6 关闭COSClient
        cosClient.shutdown();
        return signedUrl.toString();
    }




    public static void main(String[] args) {
        // 替换为实际的密钥
        secretId = "";
        secretKey = "";
        bucketName = "";
        domain = "";

//        String localFilePath = "F:\\img\\测试图\\目标图\\1.jpg";
//        String s = uploadVideo(localFilePath,"myPic","2024/09/02/20240902075035A001.jpg");// merge,myPic,user
//        System.out.println(s);

        getImageUrl("https://lilanai.com/myPic/2024/09/04/20240904161510A003.jpg");


    }
}