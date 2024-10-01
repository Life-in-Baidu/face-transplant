package com.ruoyi.biz.api.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author hzw
 * @date 2024/6/14
 */
public class ComImgUtils {

    public static void downImg(String inputPath,String outputPath) throws IOException {

        try {
            // 压缩图片
            File inputFile = new File(inputPath);
            File outputFile = new File(outputPath);

            // 使用Thumbnailator进行图片压缩
            /*Thumbnails.of(inputFile)
                    .size(1500, 1500) // 可以指定输出尺寸，如果不关心尺寸只想压缩，可以不设置此项
                    .outputQuality(1f) // 调整输出质量，0-1之间，值越小质量越低，压缩程度越高
                    .toFile(outputFile);*/

            Files.copy(inputFile.toPath(), outputFile.toPath());

            System.out.println("图片压缩完成！");
        }catch (Exception e) {
            throw new RuntimeException("图片保存失败");
        }

    }

}
