package com.github.zhuyizhuo.generator.mybatis.convention;

import com.github.zhuyizhuo.generator.mybatis.annotation.CoventionClass;
import com.github.zhuyizhuo.generator.mybatis.annotation.Value;
import com.github.zhuyizhuo.generator.mybatis.dto.JavaClassDefinition;
import com.github.zhuyizhuo.generator.mybatis.enums.FileTypeEnums;
import com.github.zhuyizhuo.generator.mybatis.enums.ModuleEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.CustomizeModuleInfo;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.FormatService;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.JavaModuleInfo;
import com.github.zhuyizhuo.generator.mybatis.generator.support.ModuleInfo;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class: FileOutPathInfo <br>
 * description: 包路径 及 文件输出路径信息 <br>
 *
 * @author yizhuo <br>
 * @since  1.0
 * @version 1.4.0
 */
@CoventionClass
public class FileOutPathInfo {

    @Value("#{generate.base.out-put-path}")
    private String baseOutputPath;
    /**  true 则生成完整目录 false 则仅生成目录最后一层 */
    @Value("#{generate.java.base.package.enabled}")
    private String basePackageEnabled;

    private String tableName;
    private String tableNameCamelCase;
    /**
     *  类名格式化 Service Map
     *  ModuleType -> 类名格式化 Service
     */
    private Map<String, FormatService> classNameFormatServieMap;
    /**
     * moduleTpye ->  ModuleInfo
     */
    private Map<String, ModuleInfo> moduleInfoMap;
    /** 模块信息 */
    private List<ModuleInfo> modules;
    /**  moduleType -> java 类定义 Map  */
    private Map<String,JavaClassDefinition> javaClassDefinitionMap;

    /**
     * 初始化模块信息
     */
    public void init() {
        ModuleEnums[] values = ModuleEnums.values();
        String outPutFullPathFormat = "";
        ModuleInfo info;
        String filePackage;
        String outPutPath;
        String absoluteOutputPath;
        for (int i = 0; i < values.length; i++) {
            info = new ModuleInfo();
            ModuleEnums module = values[i];
            filePackage = PropertiesUtils.getConfig(module.getFilePackageKey());
            outPutPath = PropertiesUtils.getConfig(module.getOutputPathKey());
            absoluteOutputPath = PropertiesUtils.getConfig(module.getAbsoluteOutputPath());
            if (FileTypeEnums.JAVA.equals(module.getTypeEnums())){
                outPutFullPathFormat = getJavaOutputFullPath(outPutPath, filePackage, absoluteOutputPath) + "{0}.java";
            } else if (FileTypeEnums.XML.equals(module.getTypeEnums())){
                outPutFullPathFormat = getResourcesOutputFullPath(outPutPath, absoluteOutputPath) + "{0}.xml";
            }
            info.setModuleType(module.toString());
            info.setFileType(module.getTypeEnums());
            info.setFileFullPackage(filePackage);
            info.setOutPutFullPathFormatPattern(outPutFullPathFormat);
            info.setFileNameFormatServie(getFormatService(module));
            info.setFileNameFormatPattern(PropertiesUtils.getConfig(module.getFileNameFormatKey()));
            addModuleInfo(module, info);
        }
    }

    public void setClassNameFormatServieMap(Map<String, FormatService> classNameFormatServieMap) {
        this.classNameFormatServieMap = classNameFormatServieMap;
    }

    /**
     * 根据路径获取资源文件输出全路径
     * @param resourcesOutPutPath 资源文件的输出路径
     * @return 资源文件输出全路径
     */
    public String getResourcesOutputFullPath(String resourcesOutPutPath, String absoluteOutputPath) {
        String outPutFullPath;
        if ("TRUE".equalsIgnoreCase(basePackageEnabled)) {
            if (absoluteOutputPath != null && !"".equals(absoluteOutputPath)) {
                outPutFullPath = absoluteOutputPath + "/" + resourcesOutPutPath;
            }else {
                outPutFullPath = baseOutputPath + "/" + resourcesOutPutPath;
            }
        } else {
            String tempPath = resourcesOutPutPath.replaceAll("\\\\","/");
            if (tempPath.lastIndexOf("/") != -1){
                tempPath = tempPath.substring(tempPath.lastIndexOf("/") + 1);
            }
            outPutFullPath = baseOutputPath + "/" + tempPath;
        }
        return outPutFullPath + "/";
    }

    /***
     *  根据文件包名获取文件输出全路径
     *
     * @param outPutPath java 文件输出路径 例如 /src/main/java
     * @param classFullPackage java 类的包路径
     * @return java 文件输出全路径
     */
    public String getJavaOutputFullPath(String outPutPath, String classFullPackage, String absoluteOutputPath) {
        String outPutFullPath;
        if ("TRUE".equalsIgnoreCase(basePackageEnabled)) {
            if (absoluteOutputPath != null && !"".equals(absoluteOutputPath)) {
                outPutFullPath = absoluteOutputPath + "/" + classFullPackage.replaceAll("\\.", "/");
            }else {
                outPutFullPath = baseOutputPath + "/" + outPutPath + "/" + classFullPackage.replaceAll("\\.", "/");
            }
        } else {
            int index = classFullPackage.lastIndexOf(".");
            if(index != -1){
                classFullPackage = classFullPackage.substring(index + 1);
            }
            outPutFullPath = baseOutputPath + "/" + classFullPackage;
        }
        return outPutFullPath + "/";
    }

    /**
     *  初始化类名 及 输出全路径
     * @param tableName 表名
     * @param tableNameCamelCase 表名驼峰命名
     */
    public void initFileNamesAndOutPutFullPath(String tableName, String tableNameCamelCase) {
        this.tableName = tableName;
        this.tableNameCamelCase = tableNameCamelCase;

        String fileName = "";
        this.modules = new ArrayList<>();
        this.javaClassDefinitionMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, ModuleInfo> entry : moduleInfoMap.entrySet()) {
            String moduleType = entry.getKey();
            ModuleInfo moduleInfo = entry.getValue();
            FormatService fileNameFormatServie = moduleInfo.getFileNameFormatServie();
            if (fileNameFormatServie != null) {
                fileName = fileNameFormatServie.format(tableName);
            } else {
                fileName = fileNameFormat(moduleInfo.getFileType(), moduleInfo.getFileNameFormatPattern());
            }

            if (FileTypeEnums.JAVA.equals(moduleInfo.getFileType())){
                String fileFullPackage = moduleInfo.getFileFullPackage();
                String camelCase = GeneratorStringUtils.firstLower(tableNameCamelCase);
                this.javaClassDefinitionMap.put(moduleType, new JavaClassDefinition(fileFullPackage, fileName, camelCase));
            }
            // 设置文件输出全路径
            moduleInfo.setOutPutFullPathByFileName(fileName);
            this.modules.add(moduleInfo);
        }
    }

    /**
     *  新增 java 模板
     * @param fileInfo java 模块信息
     */
    public void addJavaTemplate(JavaModuleInfo fileInfo) {
        ModuleInfo moduleInfo = getModuleInfo(fileInfo.getModuleType());
        if (moduleInfo == null){
            moduleInfo = new ModuleInfo();
            addModuleInfo(fileInfo.getModuleType(), moduleInfo);
        }
        String outPutFullPathFormat = getJavaOutputFullPath(fileInfo.getOutputPath(),
                                        fileInfo.getClassPackage(), null) + "{0}.java";
        moduleInfo.setModuleType(fileInfo.getModuleType());
        moduleInfo.setOutPutFullPathFormatPattern(outPutFullPathFormat);
        // 仅 java 模块需设置包路径
        moduleInfo.setFileFullPackage(fileInfo.getClassPackage());
        moduleInfo.setFileNameFormatServie(getFormatService(fileInfo.getModuleType()));
        moduleInfo.setFileType(FileTypeEnums.JAVA);
        moduleInfo.setFileNameFormatPattern(fileInfo.getClassNameFormat());
    }

    /**
     * 新增自定义模块模板
     * @param fileInfo 自定义模块信息
     */
    public void addCustomizeModule(CustomizeModuleInfo fileInfo) {
        ModuleInfo moduleInfo = getModuleInfo(fileInfo.getModuleType());
        if (moduleInfo == null){
            moduleInfo = new ModuleInfo();
            addModuleInfo(fileInfo.getModuleType(), moduleInfo);
        }

        moduleInfo.setModuleType(fileInfo.getModuleType());
        moduleInfo.setFileNameFormatPattern(fileInfo.getFileNameFormatPattern());
        moduleInfo.setFileNameFormatServie(getFormatService(fileInfo.getModuleType()));
        moduleInfo.setOutPutFullPathFormatPattern(fileInfo.getOutPutFullPathFormatPattern());
    }

    /**
     *  获取所有模块信息
     * @return 所有模块信息
     */
    public List<ModuleInfo> getAllModule(){
        return this.modules;
    }

    public Map<String,JavaClassDefinition> getJavaClassDefinitionMap(){
        return this.javaClassDefinitionMap;
    }

    public void setBasePackageEnabled(String basePackageEnabled) {
        this.basePackageEnabled = basePackageEnabled;
    }

    private void addModuleInfo(ModuleEnums value, ModuleInfo info) {
        addModuleInfo(value.toString(), info);
    }

    private void addModuleInfo(String moduleType, ModuleInfo info) {
        if (this.moduleInfoMap == null){
            this.moduleInfoMap = new ConcurrentHashMap<>();
        }
        this.moduleInfoMap.put(moduleType, info);
    }

    private ModuleInfo getModuleInfo(String moduleType) {
        return this.moduleInfoMap.get(moduleType);
    }

    /**
     *  格式化类名
     */
    private String fileNameFormat(FileTypeEnums typeEnums,String formatConfig) {
        if (FileTypeEnums.XML.equals(typeEnums)){
            return MessageFormat.format(formatConfig, tableName.toLowerCase());
        }
        return MessageFormat.format(formatConfig, tableNameCamelCase);
    }

    private FormatService getFormatService(ModuleEnums moduleType) {
        return getFormatService(moduleType.toString());
    }

    private FormatService getFormatService(String moduleType) {
        if (this.classNameFormatServieMap == null){
            return null;
        }
        return classNameFormatServieMap.get(moduleType);
    }
}