package com.ruoyi.pay.utils;

import cn.hutool.core.date.DateTime;
import java.util.Date;

/**
 * 订单号生成器
 * 参考:https://www.cnblogs.com/luochengqiuse/p/6747895.html
 *
 * @author lian.tian
 */
public class OrderGenerator {

    private static class OrderGeneratorHolder {
        private static OrderGenerator instance = new OrderGenerator();
    }

    public static OrderGenerator getInstance() {
        return OrderGeneratorHolder.instance;
    }


    /**
     * 节点 ID 默认取1
     */
    private long workerId = 1;
    /**
     * 序列id 默认取1
     */
    private long sequence = 1;

    /**
     * 机器标识位数
     */
    private final long workerIdBits = 10L;


    /**
     * 机器ID最大值
     * 结果就是2的workerBits次方-1,能表示的最大数.全部1亦或10位0，就是0开头最后10位1
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);


    /**
     * 毫秒内自增位
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID偏左移12位
     */
    private final long workerIdShift = sequenceBits;


    /**
     * 数据中心ID左移17位
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 时间毫秒左移22位
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    private long lastTimestamp = -1L;


//    public void initParam() {
//        // 从默认位置读取workerId,最大1024
//        try {
//            File conf = new File(WORKERID_PATH);
//            if (conf.exists()) {
//                String str = FileUtils.readFileToString(conf);
//                workerId = Integer.parseInt(str);
//            } else {
//                logger.warn(" worker id not found,will use default value...");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        logger.info(" worker id is {}", workerId);
//        if (workerId < 0 || workerId > maxWorkerId) {
//            throw new IllegalArgumentException("workerId is illegal: "
//                    + workerId);
//        }
//    }


    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }


    public String create() {
        return nextNo();
    }

    /**
     * 获取id 线程安全
     */
    private synchronized String nextNo() {
        long timestamp = timeGen();
        // 时间错误
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        // 当前毫秒内，则+1
        if (lastTimestamp == timestamp) {
            // 当前毫秒内计数满了，则等待下一秒
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID,最大十位数

        long id = ((timestamp % 1000) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
//        return DateTime.now().toString("yyyyMMddHHmm") + String.format("%010d", id);
        Date date = new Date();
        return DateTime.now().toString("yyyyMMddHHmm") + String.format("%010d", id);
    }

    /**
     * 等待下一个毫秒的到来
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


}

