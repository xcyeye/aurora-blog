package com.github.zhuyizhuo.generator.utils;

import com.github.zhuyizhuo.generator.mybatis.generator.support.ContextHolder;

import java.io.BufferedReader;
import java.util.Properties;

/**
 *  资源文件处理工具类
 * @author yizhuo
 * @version 1.0
 * time: 2018/7/29 18:39
 */
public class PropertiesUtils {

    public static final Properties proInfo = new Properties();

    public static void loadProperties(BufferedReader resourceAsStream) {
        try {
            proInfo.load(resourceAsStream);
        } catch (Exception e) {
            LogUtils.printException("加载配置文件失败!",e);
        }
    }

    public static String getProperties(String key){
        String property = proInfo.getProperty(key);
        return property == null ? null : property.trim();
    }

    public static String getConfig(String key){
        return GeneratorStringUtils.isBlank(PropertiesUtils.getProperties(key))
                ? ContextHolder.getDefaultConfig(key)
                : PropertiesUtils.getProperties(key);
    }

    /**
     * 判断是否包含对应配置
     * @param key 键
     * @return 存在返回true  不存在返回false
     */
    public static boolean containsKey(String key){
        return GeneratorStringUtils.isNotBlank(proInfo.getProperty(key));
    }

    /**
     * 获取配置信息
     * @param key 键
     * @return 不存在 返回FALSE
     */
    public static boolean getBooleanPropertiesDefaultFalse(String key){
        String properties = getProperties(key);
        if (GeneratorStringUtils.isBlank(properties)){
            return false;
        }
        try {
            return Boolean.parseBoolean(properties);
        } catch(Exception e) {
            LogUtils.printErrInfo("配置有误,key="+key+",值应为 true false");
        }
        return false;
    }

    /**
     * 获取配置信息
     * @param key 键
     * @return 不存在则 返回TRUE
     */
    public static boolean getBooleanPropertiesDefaultTrue(String key){
        String properties = getProperties(key);
        if (GeneratorStringUtils.isBlank(properties)){
            return true;
        }
        try {
            return Boolean.parseBoolean(properties);
        } catch(Exception e) {
            LogUtils.printErrInfo("配置有误,key="+key+",值应为 true false");
        }
        return true;
    }
}
