package com.ruoyi.biz.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author: 周周
 */
public class TimeUtils {

    /**
     * @return 昨天零点
     */
    public static Date yesterdayStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        return calendar.getTime();
    }

    /**
     * 今日零点
     */
    public static Date todayStartTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
        calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
        calendar.set(Calendar.HOUR_OF_DAY,0); //这是将当天的【时】设置为0
        return calendar.getTime();
    }

    /**
     * 今日零点
     * @return date
     */
    public static Date todayEndTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 明天零点
     */
    public static Date tomorrowTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
        calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
        calendar.set(Calendar.HOUR_OF_DAY,0); //这是将当天的【时】设置为0
        calendar.add(Calendar.DATE,1); //当前日期加一
        return calendar.getTime();
    }


    /**
     * 获得本周一0点时间
     * @return date
     */
    public static Date weekStartTime() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得本周日24点时间
     * @return date
     */
    public static Date weekEndTime() {
        Calendar calendar = Calendar.getInstance();
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 7);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获得本月第一天0点时间
     * @return date
     */
    public static Date monthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     * @return date
     */
    public static Date mouthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    /**
     * 获取当年的开始时间
     * @return 本年开始时间
     */
    public static Date yearStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当年的最后时间
     * @return 当年的最后时间
     */
    public static Date yearEndTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    public static Date afterTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
        return calendar.getTime();
    }

    /**
     * @param day 指定天数
     * @return 该天数后时间
     */
    public static Date afterTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        return calendar.getTime();
    }

    /**
     * 判断是否过期
     * @param expiryTime 过期时间
     * @return result
     */
    public static boolean overdueTime(Date expiryTime){
        Date date = new Date();
        return date.getTime() > expiryTime.getTime();
    }
}
