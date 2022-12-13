package com.github.zhuyizhuo.generator.mybatis.generator.support;

import com.github.zhuyizhuo.generator.mybatis.annotation.CoventionClass;
import com.github.zhuyizhuo.generator.mybatis.annotation.Resource;
import com.github.zhuyizhuo.generator.mybatis.annotation.Value;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.github.zhuyizhuo.generator.utils.LogUtils;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class: ContextHolder <br>
 * description: 配置扫描 自动装配 <br>
 * time: 2019/5/23
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
@Resource("generate-config.properties")
public class ContextHolder {
    /** 系统默认配置信息 */
    private static Properties contextConfig = new Properties();
    /** 生成器配置对象 map */
    private static Map<String, Object> beanMap = new ConcurrentHashMap<String, Object>();

    private List<String> classNames = new ArrayList<String>();

    public void init() {
        try {
            Class<? extends ContextHolder> aClass = this.getClass();
            //定位
            doLoadConfig(aClass.getAnnotation(Resource.class).value());
            //注册
            doRegister();
            //注入
            doAutowired();
        } catch (Exception e){
            LogUtils.printException("生成器初始化失败!",e);
        }
    }

    private void doAutowired() {
        if (beanMap.isEmpty()){return;}

        GenericTokenParser parser = new GenericTokenParser("#{", "}", new TokenHandler() {
            @Override
            public String handleToken(String content) {
                return getConfig(content);
            }
        });

        initProperties(parser);

        for (Map.Entry<String,Object> entry : beanMap.entrySet()){
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (!field.isAnnotationPresent(Value.class)){ continue;}

                String configValue = field.getAnnotation(Value.class).value().trim();
                if ("".equals(configValue)){
                    configValue = field.getType().getName();
                }

                field.setAccessible(true);
                try {
                    while (configValue.contains("#")){
                        configValue = parser.parse(configValue);
                    }
                    field.set(entry.getValue(), configValue);
                } catch (IllegalAccessException e) {
                    LogUtils.printException(e);
                }
            }
        }

    }

    private void initProperties(GenericTokenParser parser) {
        loopProperties(contextConfig, parser);
        loopProperties(PropertiesUtils.proInfo, parser);
        LogUtils.printInfo("配置信息:" + PropertiesUtils.proInfo.toString());
    }

    private void loopProperties(Properties proInfo, GenericTokenParser parser) {
        Enumeration<?> enumeration = proInfo.propertyNames();
        while (enumeration.hasMoreElements()){
            String key = (String)enumeration.nextElement();
            String property = proInfo.getProperty(key);
            if (property != null && property.trim().length() > 0){
                while (property.contains("#")){
                    property = parser.parse(property);
                }
                proInfo.setProperty(key, property);
            }
        }
    }

    private String getConfig(String key) {
        String properties = PropertiesUtils.getProperties(key);
        if (GeneratorStringUtils.isNotBlank(properties)){
            return properties.trim();
        } else {
            String property = contextConfig.getProperty(key);
            if (GeneratorStringUtils.isNotBlank(property)){
                return property.trim();
            } else {
                return System.getProperty(key);
            }
        }
    }

    private void doRegister() {
        if (classNames.isEmpty()) {return;}
        try {
            for (int i = 0; i < classNames.size(); i++) {
                String className = classNames.get(i);

                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(CoventionClass.class)){
                    String beanName = GeneratorStringUtils.firstLower(clazz.getSimpleName());
                    beanMap.put(beanName,clazz.newInstance());
                }
            }
        } catch (Exception e) {
            LogUtils.printException(e);
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(new BufferedReader(new InputStreamReader(resourceAsStream,"UTF-8")));

            resourceAsStream = classLoader.getResourceAsStream("application.properties");
            Properties contextConfig = new Properties();
            contextConfig.load(resourceAsStream);
            String property = contextConfig.getProperty("generate.convention.sourceType");
            String[] split = property.split(",");
            this.classNames = Arrays.asList(split);
        } catch (IOException e) {
            LogUtils.printException(e);
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    LogUtils.printException(e);
                }
            }
        }
    }

    public static <T> T getBean(String beanName){
        return (T) beanMap.get(GeneratorStringUtils.firstLower(beanName));
    }

    public static String getDefaultConfig(String key){
        return contextConfig.getProperty(key);
    }
}
