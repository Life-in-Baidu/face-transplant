package com.ruoyi.quartz.task;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }


    /**
     * 删除用户上次图片：30天
     */
    public void delUserImg()
    {
//        String handPath = RuoYiConfig.getUploadPath() + "/merge";
        String handPath = RuoYiConfig.getUploadPath();
        // 时间文件夹路径，例如；/2024/04/15

        // 创建一个Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date newDate = calendar.getTime();

        String divPath = StringUtils.format("/{}", DateFormatUtils.format(newDate, "yyyy/MM/dd"));
        File file = new File(handPath + divPath);
        // 调用方法尝试删除文件夹
        boolean isDeleted = deleteDirectoryRecursively(file);

        if (isDeleted) {
            System.out.println("Successfully deleted daily files (fused images for months)！");
        } else {
            System.out.println("文件夹删除失败或文件夹不存在！");
        }
    }

    /**
     * 删除用户融合图片：6个月或3天
     */
    public void delMergeImg()
    {
        String handPath = RuoYiConfig.getUploadPath() + "/merge";
        // 时间文件夹路径，例如；/2024/04/15

        // 创建一个Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date newDate = calendar.getTime();

        String divPath = StringUtils.format("/{}", DateFormatUtils.format(newDate, "yyyy/MM/dd"));
        File file = new File(handPath + divPath);
        // 调用方法尝试删除文件夹
        boolean isDeleted = deleteDirectoryRecursively(file);

        if (isDeleted) {
            System.out.println("Successfully deleted daily files (fused images for months)！");
        } else {
            System.out.println("文件夹删除失败或文件夹不存在！");
        }
    }

    public static boolean deleteDirectoryRecursively(File file) {
        if (file.isDirectory()) { // 检查是否为目录
            File[] entries = file.listFiles(); // 获取目录下的所有文件和子目录

            if (entries != null) {
                for (File entry : entries) {
                    // 递归删除每个条目
                    if (!deleteDirectoryRecursively(entry)) {
                        return false; // 如果任何一个条目无法删除，则返回false
                    }
                }
            }
        }

        // 尝试删除当前的文件或目录
        return file.delete();
    }
}
