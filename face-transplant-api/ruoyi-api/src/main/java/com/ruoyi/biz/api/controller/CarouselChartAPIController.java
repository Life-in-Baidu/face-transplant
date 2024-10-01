package com.ruoyi.biz.api.controller;

import com.ruoyi.biz.api.util.OrderNoCenter;
import com.ruoyi.biz.domain.BizLbt;
import com.ruoyi.biz.service.IBizLbtService;
import com.ruoyi.common.core.domain.ApiResult;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author hzw
 * @date 2024/5/7
 */
@Api(tags = "轮播图接口")
@Slf4j
@RequestMapping("/api/carousel")
@RestController
public class CarouselChartAPIController {

    @Autowired
    private IBizLbtService bizLbtService;

    /**
     * 查询轮播图列表
     */
    @ApiOperation("获取轮播图列表")
    @GetMapping("/list")
    public ApiResult list()
    {
        BizLbt bizLbt = new BizLbt();
        List<BizLbt> list = bizLbtService.selectBizLbtList(bizLbt);
        return ApiResult.success(list);
    }

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public ApiResult test()
    {


        String downloadUrl = ""; // 替换为实际的文件URL
        String targetFilePath = "F:\\img\\car\\新建文件夹\\"; // 指定保存的文件路径

        for (int i = 0; i < 100; i++) {
            //计算时间戳();

            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                long startTime = System.currentTimeMillis(); // 开始计时

                String s = OrderNoCenter.getInstance().create() +  ".jpg";
                s = targetFilePath + s;
                downloadFile(downloadUrl, s);
                long endTime = System.currentTimeMillis(); // 结束计时
                System.out.println("下载文件耗时：" + (endTime - startTime) + "毫秒");
                return s;
            });

        }
        return ApiResult.success("测试接口");
    }
    private static void downloadFile(String urlStr, String targetFilePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求属性
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 成功响应
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = new FileOutputStream(targetFilePath);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Download complete.");
                System.out.println("File saved to: " + targetFilePath);

                inputStream.close();
                outputStream.close();
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }

            connection.disconnect(); // 断开连接
        } catch (IOException e) {
            System.err.println("Error downloading the file: " + e.getMessage());
        }
    }
}
