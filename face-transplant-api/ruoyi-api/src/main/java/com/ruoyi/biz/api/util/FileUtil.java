package com.ruoyi.biz.api.util;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.config.RuoYiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * author: 周周
 */
@Slf4j
public class FileUtil {
    /**
     * @param url 删除文件的Url
     */
    public static boolean deleteFile(String url){
        if (!url.contains("/upload")){
            log.error("腾讯云视频资源未转成云服务器资源");
            return false;
        }
        String tempUrl = url.substring(url.indexOf("/upload"));
        url = tempUrl.replaceAll("/upload", "");
        log.debug("资源文件在服务器地址:{}", RuoYiConfig.getUploadPath()+url);
        try{
            File file = new File(RuoYiConfig.getUploadPath()+url);
            if (file.exists())log.debug("文件存在");
            else log.debug("文件不存在");
            return file.delete();
        }catch (Exception e){
            log.error("删除文件失败:{}",e.getMessage());
        }
        return false;
    }

    /**
     * @param url 文件url https://yoface.blueasiainfo.com/profile/upload/2022/01/12/755a0acdfa7947db943b51282be05aa3.mp4
     */
    public static String interceptUrl(String url){
        int indexOf = url.indexOf("2");
        return url.substring(indexOf);
    }

    /**
     * 判断文件类型
     * @param file
     * @return
     */
    public static boolean judgeType(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return "png,jpeg,jpg,PNG,JPG,JPEG".contains(substring);
    }

    /**
     * @param str str
     * @return 是否为json格式
     */
    public static boolean isJsonString(String str) {
        boolean result = false;
        try {
            Object obj=JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result=false;
        }
        return result;
    }
}
