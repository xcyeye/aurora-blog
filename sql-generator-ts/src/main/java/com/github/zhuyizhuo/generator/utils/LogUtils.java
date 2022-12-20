package com.github.zhuyizhuo.generator.utils;

import com.github.zhuyizhuo.generator.mybatis.generator.extension.LogService;

/**
 * class: LogUtils <br>
 * description: 日志输出 <br>
 * time: 2018/8/6 11:04
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class LogUtils {

    public static String logLevel;
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_DEBUG = "DEBUG";

    private static LogService logService;

    public static void setLogService(LogService logService) {
        LogUtils.logService = logService;
    }

    public static void printInfo(String info){
        if (LEVEL_INFO.equalsIgnoreCase(logLevel)){
            System.out.println(info);
        }
    }

    public static void printErrInfo(String info){
        if (LEVEL_INFO.equalsIgnoreCase(logLevel)){
            System.err.println(info);
        }
    }

    public static void printException(Exception e){
        if (LEVEL_INFO.equalsIgnoreCase(logLevel)){
            e.printStackTrace();
        }
    }

    public static void printException(String errorMsg, Exception e){
        if (LEVEL_INFO.equalsIgnoreCase(logLevel)){
            System.err.println(errorMsg);
            e.printStackTrace();
        }
    }

    public static void logGenerateInfo(Object obj){
        if (LEVEL_INFO.equalsIgnoreCase(logLevel) && logService != null){
            logService.logGenerateInfo(obj);
        }
    }

}
