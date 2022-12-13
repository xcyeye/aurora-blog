package com.github.zhuyizhuo.generator.mybatis.generator.support;

import com.github.zhuyizhuo.generator.mybatis.enums.FileTypeEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.FormatService;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.google.common.base.CharMatcher;

import java.text.MessageFormat;

/**
 * class: ModuleInfo <br>
 * description: 模块信息 <br>
 * time: 2019/5/27
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class ModuleInfo {
    /** 模块类型 */
    private String moduleType;
    /** 文件类型 */
    private FileTypeEnums fileType;
    /** 文件包路径 仅 java 模块需设置包路径 */
    private String fileFullPackage;
    /**
     *  文件名格式化
     *  例如 {0}Mapper
     * @See java.text.MessageFormat#format(java.lang.String, java.lang.Object...)
     * fileNameFormatPattern 为第一个参数
     * 表名转驼峰命名 为第二个参数
     *
     * 优先级低于 ModuleInfo#fileNameFormatServie
     * 如果设置了 fileNameFormatServie 则 该配置自动失效
     */
    private String fileNameFormatPattern;
    /**
     *  文件输出路径模板
     *  @See java.text.MessageFormat#format(java.lang.String, java.lang.Object...)
     *  outPutFullPathFormatPattern 为 第一个参数
     *  文件名为第二个参数
     */
    private String outPutFullPathFormatPattern;
    /**
     * 文件名格式化 service
     * 可使用  GeneratorBuilder.addModuleNameFormat(moduleType,moduleNameFormatService)
     * 自定义文件名格式化 Service
     */
    private FormatService fileNameFormatServie;
    /**
     * 文件输出全路径
     * 将文件输出路径模板格式化后 设置
     */
    private String outPutFullPath;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getFileFullPackage() {
        return fileFullPackage;
    }

    public void setFileFullPackage(String fileFullPackage) {
        this.fileFullPackage = fileFullPackage;
    }

    public String getOutPutFullPathFormatPattern() {
        return outPutFullPathFormatPattern;
    }

    public void setOutPutFullPathFormatPattern(String outPutFullPathFormatPattern) {
        this.outPutFullPathFormatPattern = outPutFullPathFormatPattern;
    }

    public FormatService getFileNameFormatServie() {
        return fileNameFormatServie;
    }

    public void setFileNameFormatServie(FormatService fileNameFormatServie) {
        this.fileNameFormatServie = fileNameFormatServie;
    }

    public String getOutPutFullPath() {
        if (GeneratorStringUtils.isNotBlank(this.outPutFullPath)){
            String collapsePath = CharMatcher.is('/').collapseFrom(this.outPutFullPath, '/');
            String realPath = CharMatcher.is('\\').collapseFrom(collapsePath, '/');
            return realPath;
        }
        return outPutFullPath;
    }

    /**
     *  设置文件全路径
     * @param fileName 文件名
     */
    public void setOutPutFullPathByFileName(String fileName) {
        this.outPutFullPath = MessageFormat.format(getOutPutFullPathFormatPattern(), fileName);
    }

    public FileTypeEnums getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeEnums fileType) {
        this.fileType = fileType;
    }

    public String getFileNameFormatPattern() {
        return fileNameFormatPattern;
    }

    public void setFileNameFormatPattern(String fileNameFormatPattern) {
        this.fileNameFormatPattern = fileNameFormatPattern;
    }
}
