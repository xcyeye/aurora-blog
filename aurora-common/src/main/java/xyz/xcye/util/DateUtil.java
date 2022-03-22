package xyz.xcye.util;

import xyz.xcye.enums.CommonFormatEnum;
import java.util.Date;

/**
 * 与时间相关的工具类
 * @author qsyyke
 */


public class DateUtil {
    /**
     * 获得指定日期的年部分
     * @param date 时间对象 
     * @return
     */
    public static int getYear(Date date) {
        return cn.hutool.core.date.DateUtil.year(date);
    }

    /**
     * 获得指定日期的年部分
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getYear(String dateStr) {
        return cn.hutool.core.date.DateUtil.year(cn.hutool.core.date.DateUtil.parse(dateStr));
    }

    /**
     * 获得指定日期的月份 从1开始
     * @param date 时间对象
     * @return
     */
    public static int getMonth(Date date) {
        //返回的时间是从0开始的
        return cn.hutool.core.date.DateUtil.month(date) + 1;
    }

    /**
     * 获得指定日期的月份 从1开始
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getMonth(String dateStr) {
        //返回的时间是从0开始的
        return cn.hutool.core.date.DateUtil.month(cn.hutool.core.date.DateUtil.parse(dateStr)) + 1;
    }

    /**
     * 获得指定日期是这个日期所在月份的第几天 boolean is24HourClock
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        return cn.hutool.core.date.DateUtil.dayOfMonth(date);
    }

    /**
     * 获得指定日期是这个日期所在月份的第几天 boolean is24HourClock
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getDay(String dateStr) {
        return cn.hutool.core.date.DateUtil.dayOfMonth(cn.hutool.core.date.DateUtil.parse(dateStr));
    }

    /**
     * 返回指定日期的小时部分
     * @param date 日期
     * @param is24HourClock 是否是24小时制
     * @return
     */
    public static int getHour(Date date, boolean is24HourClock) {
        return cn.hutool.core.date.DateUtil.hour(date,is24HourClock);
    }

    /**
     * 返回指定日期的小时部分
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param is24HourClock 是否是24小时制
     * @return
     */
    public static int getHour(String dateStr, boolean is24HourClock) {
        return cn.hutool.core.date.DateUtil.hour(cn.hutool.core.date.DateUtil.parse(dateStr),is24HourClock);
    }

    /**
     * 返回指定日期的小时部分
     * @param date 日期
     * @return
     */
    public static int getHour(Date date) {
        return cn.hutool.core.date.DateUtil.hour(date,false);
    }

    /**
     * 返回指定日期的小时部分
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getHour(String dateStr) {
        return cn.hutool.core.date.DateUtil.hour(cn.hutool.core.date.DateUtil.parse(dateStr),false);
    }

    /**
     * 获得指定日期的分部分
     * @param date 日期
     * @return
     */
    public static int getMinute(Date date) {
        return cn.hutool.core.date.DateUtil.minute(date);
    }

    /**
     * 获得指定日期的分部分
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getMinute(String dateStr) {
        return cn.hutool.core.date.DateUtil.minute(cn.hutool.core.date.DateUtil.parse(dateStr));
    }

    /**
     * 获得指定日期的秒数部分
     * @param date 日期
     * @return
     */
    public static int getSecond(Date date) {
        return cn.hutool.core.date.DateUtil.second(date);
    }

    /**
     * 获得指定日期的秒数部分
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getSecond(String dateStr) {
        return cn.hutool.core.date.DateUtil.second(cn.hutool.core.date.DateUtil.parse(dateStr));
    }

    /**
     * 根据特定格式，格式化日期
     * @param date 日期
     * @param format 日期格式
     * @return
     */
    public static String format(Date date,String format) {
        return cn.hutool.core.date.DateUtil.format(date,format);
    }

    /**
     * 将指定日期格式化为默认格式
     * @param date 日期
     * @return
     */
    public static String format(Date date) {
        return cn.hutool.core.date.DateUtil.format(date, CommonFormatEnum.DATE_FORMAT.getFormat());
    }
}
