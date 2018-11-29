package com.huateng.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 文件描述
 *
 * @author 陈晓伟 on 2017/1/17
 * @version 1.0
 */
public class DateUtils {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static String getStringOfDateTime() {
        return format(DEFAULT_FORMAT);
    }

    public static String formatTimestamp(int ts) {
        Date date = new Date(ts);
        return format("yyyy-MM-dd HH:mm:ss");
    }

    public static String format(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parse(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String addMoths(String date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parse(date, DateUtils.yyyy_MM_dd));
        int orginalDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);
        String str = DateUtils.format(calendar.getTime(), DateUtils.yyyy_MM_dd);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (maxDay < orginalDay) {
            calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, orginalDay - 1);
        }
        return DateUtils.format(calendar.getTime(), DateUtils.yyyy_MM_dd);
    }

    /**
     * 在指定日期上加上给定天数，得出实际日期
     *
     * @param date
     * @param days
     * @return
     */
    public static String addDays(String date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parse(date, DateUtils.yyyy_MM_dd));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return DateUtils.format(calendar.getTime(), DateUtils.yyyy_MM_dd);
    }

    /**
     * 在指定日期上加上给定天数，得出实际日期
     *
     * @param date 日期
     * @param days 天数
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();
    }

    /**
     * 在指定日期上加上给定天数，得出实际日期
     *
     * @param date
     * @param days
     * @return
     */
    public static String getPreDay(String date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.parse(date, DateUtils.yyyy_MM_dd));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days - 1);
        return DateUtils.format(calendar.getTime(), DateUtils.yyyy_MM_dd);
    }

    /**
     * 日期相减，获取天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int subDate(Date date1, Date date2) {
        BigDecimal ts = BigDecimal.valueOf(date1.getTime() - date2.getTime());
        BigDecimal day = ts.divide(BigDecimal.valueOf(1000 * 3600 * 24));
        return day.intValue();
    }

    public static void main(String[] arg) {
        System.out.println(getNowDate("yyyy-MM-dd"));
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param d1 d1
     * @param d2 d2
     * @return boolean
     */
    @SuppressWarnings("deprecation")
    public static boolean isSameDate(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth()
                    && d1.getDate() == d2.getDate()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前日期
     * @param pattern 格式
     * @return
     */
    public static Date getNowDate(String pattern) {
        if(StringUtils.isEmpty(pattern)){
            pattern = DEFAULT_FORMAT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(format.format(new Date()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
