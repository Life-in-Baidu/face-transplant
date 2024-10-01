package com.ruoyi.biz.utils;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * 视频或者图片下载工具类
 * @author 无聊的风
 */
@Slf4j
@Component
public class VideoDownloadUtils {

    /**
     * 下载视频
     * @param videoUrl 视频网络地址
     * @param downloadPath  视频保存地址
     */
    public static boolean downVideo(String videoUrl, String downloadPath) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        RandomAccessFile randomAccessFile = null;
        boolean re;
        try {
 
            URL url = new URL(videoUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Range", "bytes=0-");
            connection.connect();
            if (connection.getResponseCode() / 100 != 2) {
                System.out.println("连接失败...");
                return false;
            }
            inputStream = connection.getInputStream();
            int downloaded = 0;
            int fileSize = connection.getContentLength();
            randomAccessFile = new RandomAccessFile(downloadPath, "rw");
            while (downloaded < fileSize) {
                byte[] buffer = null;
                if (fileSize - downloaded >= 1000000) {
                    buffer = new byte[1000000];
                } else {
                    buffer = new byte[fileSize - downloaded];
                }
                int read = -1;
                int currentDownload = 0;
                long startTime = System.currentTimeMillis();
                while (currentDownload < buffer.length) {
                    read = inputStream.read();
                    buffer[currentDownload++] = (byte) read;
                }
                long endTime = System.currentTimeMillis();
                double speed = 0.0;
                if (endTime - startTime > 0) {
                    speed = currentDownload / 1024.0 / ((double) (endTime - startTime) / 1000);
                }
                randomAccessFile.write(buffer);
                downloaded += currentDownload;
                randomAccessFile.seek(downloaded);
                System.out.printf(downloadPath+"下载了进度:%.2f%%,下载速度：%.1fkb/s(%.1fM/s)%n", downloaded * 1.0 / fileSize * 10000 / 100,
                        speed, speed / 1000);
            }
            re = true;
            return re;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            re = false;
            return re;
        } catch (IOException e) {
            e.printStackTrace();
            re = false;
            return re;
        } finally {
            try {
                connection.disconnect();
                inputStream.close();
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取并创建视频保存路径
     * @return
     */
    public static String createVideoPath() {
        // 若依地址 RuoYiConfig.getUploadPath();
        // 手写路径 "F:\\img\\car\\";
        String handPath = RuoYiConfig.getUploadPath() + "/merge";

        // 时间文件夹路径，例如；/2024/04/15
        Date now = new Date();
        String divPath = StringUtils.format("/{}/", DateFormatUtils.format(now, "yyyy/MM/dd"));
        // 生成视频名称，也可以调用订单号工具类
        String fileName = "video" + System.currentTimeMillis() + ".jpg";
        String resourcePath = handPath + divPath + fileName;
        System.out.println("全部文件路径：" + resourcePath);

        // 创建文件
        File dir = new File(handPath + divPath);
        if (!dir.exists()) {
            dir.mkdirs();
            File file = new File(resourcePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("创建文件:{} 失败", resourcePath);
                }
            }
        }
        return resourcePath;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        // 视频地址
        String txUrl = "https://imagestest-1253534368.cos.ap-guangzhou.myqcloud.com/facefusion/ronghe_demo.mov";
        // 获取保存地址
        String resourcePath = createVideoPath();
        boolean downVideo = downVideo(txUrl, resourcePath);
        if (downVideo) {
            System.out.println("下载成功");
        } else {
            System.out.println("下载失败");
        }
    }
}