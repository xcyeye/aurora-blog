package com.github.zhuyizhuo.generator.mybatis.generator;

import com.github.zhuyizhuo.generator.mybatis.annotation.NotNull;
import com.github.zhuyizhuo.generator.mybatis.annotation.Nullable;
import com.github.zhuyizhuo.generator.mybatis.constants.ConfigConstants;
import com.github.zhuyizhuo.generator.mybatis.convention.FileOutPathInfo;
import com.github.zhuyizhuo.generator.mybatis.database.service.abstracted.AbstractDbService;
import com.github.zhuyizhuo.generator.mybatis.enums.MethodEnums;
import com.github.zhuyizhuo.generator.mybatis.enums.ModuleEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.CustomizeModuleInfo;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.FormatService;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.JavaModuleInfo;
import com.github.zhuyizhuo.generator.mybatis.generator.service.GenerateService;
import com.github.zhuyizhuo.generator.mybatis.generator.support.ContextHolder;
import com.github.zhuyizhuo.generator.mybatis.generator.support.MethodInfo;
import com.github.zhuyizhuo.generator.utils.*;
import com.google.common.base.Charsets;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.type.JdbcType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class: GeneratorBuilder <br>
 * description: Builds {@link Generator} instances. <br>
 *
 * @author yizhuo <br>
 * @version 1.2.0
 */
public class GeneratorBuilder {
    /***
     * key 数据库字段类型
     * value java 数据类型
     */
    private Map<String, Class<?>> typeMapper;
    /**
     * 指定方法名格式化 service map , 优先级高于 格式化全部方法
     */
    private Map<MethodEnums,FormatService> methodNameFormatServiceMap;
    /**
     * 格式化全部方法 service 优先级低于指定方法格式化 service
     */
    private FormatService commonMethodFormatService;
    /***
     *  模块名格式化 Service MAP
     */
    private Map<String, FormatService> moduleNameFormatServiceMap;
    /** 扩展 自定义模板 */
    private List<JavaModuleInfo> javaTemplates;
    /** 扩展 自定义模板 */
    private List<CustomizeModuleInfo> customizeModuleInfos;
    /** 自定义生成器 */
    private GenerateService generateService;
    /** 配置信息 */
    private Properties proInfo;

    public GeneratorBuilder() {
    }

    /**
     * 设置 properties
     * @param defaultProperties the properties to set.
     * @return the current builder
     */
    public GeneratorBuilder properties(String... defaultProperties) {
        return properties(getMapFromKeyValuePairs(defaultProperties));
    }

    /**
     * 设置 properties
     * @param defaults  the default properties
     * @return the current builder
     */
    public GeneratorBuilder properties(Map<String, Object> defaults) {
        if (this.proInfo == null){
            this.proInfo = new Properties();
        }
        this.proInfo.putAll(defaults);
        return this;
    }

    /**
     * 自定义指定模块生成名称
     * @param moduleType 模块类型
     * @param moduleNameFormatService 模块名格式化 Service
     * @since 1.4.0
     * @return the current builder
     */
    public GeneratorBuilder addModuleNameFormat(@NotNull ModuleEnums moduleType, @NotNull FormatService moduleNameFormatService) {
        CheckUtils.assertNotNull(moduleType,"addModuleNameFormat 参数 moduleType 不能为空!");
        this.addModuleNameFormat(moduleType.toString(), moduleNameFormatService);
        return this;
    }

    /**
     * 自定义指定模块生成名称 此方法可用来扩展自定义模块
     * @param moduleType 模块类型
     * @param moduleNameFormatService 模块名格式化 Service
     * @since 1.4.0
     * @return the current builder
     */
    public GeneratorBuilder addModuleNameFormat(@NotNull String moduleType, @NotNull FormatService moduleNameFormatService) {
        CheckUtils.assertNotNull(moduleType,"addModuleNameFormat 参数 moduleType 不能为空!");
        CheckUtils.assertNotNull(moduleNameFormatService,"addModuleNameFormat 参数 moduleNameFormatService 不能为空!");
        if (this.moduleNameFormatServiceMap == null){
            this.moduleNameFormatServiceMap = new HashMap<>();
        }
        this.moduleNameFormatServiceMap.put(moduleType, moduleNameFormatService);
        return this;
    }

    /**
     * 自定义方法生成名称
     * @param methodType 方法类型
     *       如果自定义全局方法生成格式化 则 传入 MethodEnums.ALL_METHOD
     *         全局方法名格式化需配合配置  generate.java.method.{methodType}.name-format 使用
     * @param methodNameFormatService 格式化service
     * @since 1.4.0
     * @return the current builder
     */
    public GeneratorBuilder addMethodNameFormat(@NotNull MethodEnums methodType,@NotNull FormatService methodNameFormatService) {
        CheckUtils.assertNotNull(methodType,"addMethodNameFormat 参数 methodType 不能为空!");
        CheckUtils.assertNotNull(methodNameFormatService,"addMethodNameFormat 参数 methodNameFormatService 不能为空!");
        if (MethodEnums.ALL_METHOD.equals(methodType)) {
            this.commonMethodFormatService = methodNameFormatService;
        } else {
            if (this.methodNameFormatServiceMap == null){
                this.methodNameFormatServiceMap = new ConcurrentHashMap<>();
            }
            this.methodNameFormatServiceMap.put(methodType, methodNameFormatService);
        }
        return this;
    }

    /**
     * 自定义数据库与 java 实体类型映射
     *
     * 生成器仅收录了常用类型的转换 如果生成时发现数据库类型未转换成对应 java 数据类型
     * 或者转换的 java 数据类型不是自己想要的类型 可使用此方法新增或修改类型转换
     *
     * 例如数据库类型为 NUMBER 生成器默认映射为 Integer
     * 如果想映射为 String ,设置如下：
     * new GeneratorBuilder().fieldType2JavaType("NUMBER",String.class);
     *
     * @param dataBaseType  数据类型
     * @param javaTypeClass java 类
     * @return the current builder
     */
    public GeneratorBuilder fieldType2JavaType(@NotNull String dataBaseType, @NotNull Class<?> javaTypeClass) {
        CheckUtils.assertNotNull(dataBaseType,"fieldType2JavaType 请指定 dataBaseType, 即数据库字段类型.");
        CheckUtils.assertNotNull(javaTypeClass,"fieldType2JavaType 请指定 javaTypeClass, 即 JAVA 字段类型.");

        if (this.typeMapper == null){
            this.typeMapper = new HashMap<String, Class<?>>();
        }
        this.typeMapper.put(dataBaseType.toUpperCase(), javaTypeClass);
        return this;
    }

    /**
     * 自定义数据库与 mybatis xml 中 jdbcType 类型映射
     *
     * 生成器仅收录了常用类型的转换 如果生成时发现数据库类型未转换成对应 jdbcType 数据类型
     * 或者转换的 jdbcType 数据类型有误 可使用此方法新增或修改类型转换
     *
     * 例如时间类型 DATE 默认映射为 TIMESTAMP , 如果将 XML 生成类型改为 DATE ,可如下设置
     * new GeneratorBuilder().fieldType2JavaType("DATE", JdbcType.DATE);
     *
     * @param dataBaseType  数据类型
     * @param jdbcType      mybatis 配置文件中类型 如 #{id,jdbcType=VARCHAR}
     * @return the current builder
     */
    public GeneratorBuilder fieldType2JdbcType(@NotNull String dataBaseType, @NotNull JdbcType jdbcType) {
        CheckUtils.assertNotNull(dataBaseType,"fieldType2JavaType 请指定 dataBaseType, 即数据库字段类型.");
        CheckUtils.assertNotNull(dataBaseType,"fieldType2JavaType 请指定 jdbcType, 即 mybatis xml 中 jdbcType.");

        TypeConversion.addType2JdbcType(dataBaseType, jdbcType.toString());
        return this;
    }

    /**
     * 添加自定义 java 模板
     * @param fileInfo java 模板信息
     * @return the current builder
     */
    public GeneratorBuilder addJavaTemplate(@NotNull JavaModuleInfo fileInfo){
        CheckUtils.assertNotNull(fileInfo,"添加模板不能为空!");

        if (this.javaTemplates == null){
            this.javaTemplates = new ArrayList<>();
        }
        this.javaTemplates.add(fileInfo);
        return this;
    }

    /**
     * 添加自定义模板
     * @param customizeModuleInfo 自定义模板信息
     * @return the current builder
     */
    public GeneratorBuilder addCustomizeModuleTemplate(@NotNull CustomizeModuleInfo customizeModuleInfo){
        CheckUtils.assertNotNull(customizeModuleInfo,"添加模板不能为空!");

        if (this.customizeModuleInfos == null){
            this.customizeModuleInfos = new ArrayList<>();
        }
        this.customizeModuleInfos.add(customizeModuleInfo);
        return this;
    }

    /**
     * 可自定义 生成器
     * @param generateService  生成器 service
     * @return the current builder
     */
    public GeneratorBuilder addGenerateService(@NotNull GenerateService generateService){
        CheckUtils.assertNotNull(generateService,"generateService 不能为空!");
        this.generateService = generateService;
        return this;
    }

    public Generator build(){
        return build("");
    }

    /**
     * Builds {@link Generator} instances.
     * @param configPath 配置文件路径
     * @return Generator 生成器
     */
    public Generator build(@Nullable String configPath) {
        try {
            if (GeneratorStringUtils.isNotBlank(configPath)){
                InputStream resourceAsStream = Resources.getResourceAsStream(configPath);
                PropertiesUtils.loadProperties(new BufferedReader(new InputStreamReader(resourceAsStream,Charsets.UTF_8)));
            }
            if (this.proInfo != null){
                PropertiesUtils.proInfo.putAll(proInfo);
            }
            CheckUtils.checkDBType();
            CheckUtils.checkNeedConfig();
        } catch (IllegalArgumentException ie){
            LogUtils.printErrInfo(ie.getMessage());
        } catch (Exception e){
            LogUtils.printException("加载资源文件失败! 请检查配置文件路径. ", e);
            System.out.println(e.getMessage());
        }
        TypeConversion.init(typeMapper);

        ContextHolder context = new ContextHolder();
        context.init();
        // 初始化常量
        AbstractDbService.tableRegex = PropertiesUtils.getConfig(ConfigConstants.TABLE_SEPARATOR);
        AbstractDbService.fieldRegex = PropertiesUtils.getConfig(ConfigConstants.FIELD_SEPARATOR);
        LogUtils.logLevel = PropertiesUtils.getConfig(ConfigConstants.LOG_LEVEL);

        FileOutPathInfo fileOutPathInfo = ContextHolder.getBean("FileOutPathInfo");
        // 需先设置格式化 service
        fileOutPathInfo.setClassNameFormatServieMap(moduleNameFormatServiceMap);
        fileOutPathInfo.init();

        Generator generator = new Generator(fileOutPathInfo, new MethodInfo(methodNameFormatServiceMap, commonMethodFormatService));
        if (javaTemplates != null && javaTemplates.size() > 0){
            for (int i = 0; i < javaTemplates.size(); i++) {
                generator.addJavaTemplate(javaTemplates.get(i));
            }
        }
        if (customizeModuleInfos != null && customizeModuleInfos.size() > 0){
            for (int i = 0; i < customizeModuleInfos.size(); i++) {
                generator.addCustomizeModuleInfo(customizeModuleInfos.get(i));
            }
        }
        generator.initGenerateService(generateService);
        return generator;
    }

    private Map<String, Object> getMapFromKeyValuePairs(String[] properties) {
        Map<String, Object> map = new HashMap<>();
        for (String property : properties) {
            int index = lowestIndexOf(property, ":", "=");
            String key = (index > 0) ? property.substring(0, index) : property;
            String value = (index > 0) ? property.substring(index + 1) : "";
            map.put(key, value);
        }
        return map;
    }

    private int lowestIndexOf(String property, String... candidates) {
        int index = -1;
        for (String candidate : candidates) {
            int candidateIndex = property.indexOf(candidate);
            if (candidateIndex > 0) {
                index = (index != -1) ? Math.min(index, candidateIndex) : candidateIndex;
            }
        }
        return index;
    }

}