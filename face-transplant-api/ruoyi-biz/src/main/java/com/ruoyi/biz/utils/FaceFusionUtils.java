package com.ruoyi.biz.utils;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.CommonClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ft.v20200304.models.FaceRect;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.DetectFaceRequest;
import com.tencentcloudapi.iai.v20200303.models.DetectFaceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author :juiwi
 * @date :2024/4/25 0:05
 * @description :some description
 */
@Slf4j
@Component
public class FaceFusionUtils {

    /**
     * 密钥id
     */
    public static String secretId;

    /**
     * 密钥key
     */
    public static String secretKey;

    public static final String PRODUCT_NAME = "facefusion";

    public static final String VERSION = "2022-09-27";

    public static final String REGION = "ap-guangzhou";

    public static final String ACTION = "FuseFacePro";

    public static String getSecretId() {
        return secretId;
    }

    @Value("${tencent.secretId}")
    public void setSecretId(String secretId) {
        FaceFusionUtils.secretId = secretId;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${tencent.secretKey}")
    public void setSecretKey(String secretKey) {
        FaceFusionUtils.secretKey = secretKey;
    }


    public static String merge(List<String> urls, String modelUrl, Integer logoAdd){
        // 更换存储桶临时路径
        for (int i = 0; i < urls.size(); i++) {
            if (StringUtils.isEmpty(urls.get(i))) {
                break;
            }
            String imageUrl = UploadTenUtils.getImageUrl(urls.get(i));
            urls.set(i, imageUrl);
        }

        logoAdd = 0;
        Credential cred = new Credential(FaceFusionUtils.getSecretId(), FaceFusionUtils.getSecretKey());
        CommonClient client = new CommonClient(FaceFusionUtils.PRODUCT_NAME, FaceFusionUtils.VERSION, cred, FaceFusionUtils.REGION);
        List<Map> list = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("Url", urls.get(0));

        list.add(map1);
        if (urls.size() >= 2) {
            List<FaceRect> faceRectList = selectFace(modelUrl);
            map1.put("TemplateFaceRect", faceRectList.get(0));
            list.remove(0);
            list.add(map1);
            for (int i = 1; i < urls.size(); i++) {
                if (StringUtils.isEmpty(urls.get(i))) {
                    break;
                }

                Map<String, Object> map2 = new HashMap<>();
                map2.put("Url", urls.get(i));
                try {
                    map2.put("TemplateFaceRect", faceRectList.get(i));
                }catch (Exception e) {
                    log.error("人脸识别异常", e);
                    throw new RuntimeException("合成图片数量不匹配，请重新上传");
                }
                list.add(map2);
            }
        }

        modelUrl = UploadTenUtils.getImageUrl(modelUrl);
        JSONObject json = new JSONObject();
        json.putOpt("RspImgType", "url"); // 返回图像方式
        json.putOpt("MergeInfos", list); // 用户图片
        json.putOpt("ModelUrl", modelUrl);    // 模板图片url
        json.putOpt("LogoAdd", logoAdd); // 融合标识
        String resp = null;


        try {
            resp = client.call(FaceFusionUtils.ACTION, json.toString());
            log.info("resp:" + resp);
        } catch (TencentCloudSDKException e) {
            log.error("人脸融合异常", e);
            throw new RuntimeException(e.getMessage());
        }

        JSONObject respJson = new JSONObject(resp);
        JSONObject o = (JSONObject) respJson.get("Response");
        String res = (String) o.get("FusedImage");


        //保存图片到本地（或服务器）
        String videoPath = VideoDownloadUtils.createVideoPath();
        boolean downVideo = VideoDownloadUtils.downVideo(res, videoPath);

        String url = UploadTenUtils.uploadVideo(videoPath, "merge", videoPath.substring(35));
        FileUtils.deleteFile(videoPath); // 删除原图
        return url;
    }


    public static List<FaceRect> selectFace(String url) {
        try{
            url = UploadTenUtils.getImageUrl(url); // 获取存储桶临时路径
            Credential cred = new Credential(FaceFusionUtils.getSecretId(), FaceFusionUtils.getSecretKey());
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, "ap-beijing", clientProfile);



            // 实例化一个请求对象,每个接口都会对应一个request对象
            DetectFaceRequest req = new DetectFaceRequest();
            req.setUrl(url);
            req.setMaxFaceNum(4L);
            // 返回的resp是一个DetectFaceResponse的实例，与请求对象对应
            DetectFaceResponse resp = client.DetectFace(req);
            String jsonString = AbstractModel.toJsonString(resp);
            System.out.println(jsonString);

            // 获取图片位置信息并保存
            Map<String,Object> map = JSON.parseObject(jsonString);
            Object faceInfos = map.get("FaceInfos");
            List list = (List) faceInfos;
            List<FaceRect> faceRectList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Object faceInfo = ((List)faceInfos).get(i);
                Object x = ((Map<String,Object>)faceInfo).get("X");
                Object y = ((Map<String,Object>)faceInfo).get("Y");
                Object width = ((Map<String,Object>)faceInfo).get("Width");
                Object height = ((Map<String,Object>)faceInfo).get("Height");

                FaceRect faceRect = new FaceRect();
                faceRect.setX(Long.parseLong(x.toString()));
                faceRect.setY(Long.parseLong(y.toString()));
                faceRect.setWidth(Long.parseLong(width.toString()));
                faceRect.setHeight(Long.parseLong(height.toString()));
                faceRectList.add(faceRect);
            }

            return faceRectList;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        secretId = "";
        secretKey = "";
        List<String> urls = new ArrayList<>();
        urls.add("https://lilanai.com/uesrMg/2024/09/04/20240904161543A004.jpg");
        String merge = merge(urls,
                "https://lilanai.com/myPic/2024/09/04/20240904161510A003.jpg", 1);
        System.out.println(merge);

//        String s = "https://lilanai.com/myPic/2024/09/04/20240904161510A003.jpg";
//        List<FaceRect> faceRectList = selectFace(s);

    }

}
