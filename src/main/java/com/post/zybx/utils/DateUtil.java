package com.post.zybx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author itle
 * @Date 2021/12/2
 */
public class DateUtil {

    private static  final String YYYYMMDD = "yyyy-MM-dd";

    private static final String YYYYMMDD_ZH = "yyyy年MM月dd日";


    /**
     * 符串转日期
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, null);
    }


    /**
     * 字符串转日期
     * @param strDate 例："2021-10-01 00:00:00"
     * @param pattern   "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static Date parseDate(String strDate, String pattern) {
        Date date = null;
        if (pattern == null) {
            pattern = YYYYMMDD;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String formatDate(Date date) {
        return formatDate(date, null);
    }

    /**
     * 日期转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        String  strDate = null;
        if (pattern == null) {
            pattern = YYYYMMDD;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            strDate = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 传入日期，获取对应年份
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.YEAR);
    }


    /**
     * 传入日期，获取对应月份
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.MONTH) + 1;
    }


    /**
     * 取得当天是多少日
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 取得当前日期是周几
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.DAY_OF_WEEK) - 1;
    }




    /**
     * 取得一年的第几周
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        return cale.get(Calendar.DAY_OF_YEAR);
    }


}
