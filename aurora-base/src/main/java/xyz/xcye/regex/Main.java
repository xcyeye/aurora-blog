package xyz.xcye.regex;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.lang.Assert;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 */


public class Main {
    public static void main(String[] args) {
        /*Calendar calendar = new GregorianCalendar();
        int i = calendar.get(Calendar.YEAR);
        System.out.println(i);

        String dateStr = "2023-03-01 19:45:56";
        Date date = DateUtil.parse(dateStr);
        System.out.println("年: " + DateUtil.year(date));
        System.out.println("月: " + DateUtil.month(date));
        System.out.println("日: " + DateUtil.dayOfMonth(date));
        System.out.println("时: " + DateUtil.hour(date,false));
        System.out.println("分: " + DateUtil.minute(date));
        System.out.println("秒: " + DateUtil.second(date));
        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        Month month = dateTime.monthEnum();

        System.out.println(Pattern.matches("yyyy-MM-dd ","yyyy-MM-dd HH:mm:ss"));*/

        System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }
}
