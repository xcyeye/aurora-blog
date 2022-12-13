package com.github.zhuyizhuo.generator.mybatis.generator.extension;

import com.github.zhuyizhuo.generator.mybatis.annotation.NotNull;
import com.github.zhuyizhuo.generator.utils.CheckUtils;

/**
 * class: JavaModuleInfo <br>
 * description: 扩展 java 模块信息 <br>
 * time: 2019/5/27
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class JavaModuleInfo {
    /** 模块 例如 controller service 等 */
    private String moduleType;
    /** 模板路径 使用模板生成时 需要传入模板路径 */
    private String templatePath;
    /** 类的包路径 例如 com.baidu **/
    private String classPackage;
    /** 输出路径 */
    private String outputPath;
    /**
     *  文件名格式化 例如 {0}Mapper  适用于批量及单表生成
     *  或直接指定文件名 例如 UserMapper 仅适用于单表生成
     */
    private String classNameFormat;

    public JavaModuleInfo(@NotNull String moduleType, @NotNull String templatePath,
                          @NotNull String classPackage, @NotNull String outputPath, @NotNull String classNameFormat) {
        CheckUtils.assertNotNull(moduleType,"moduleType must not null!");
        CheckUtils.assertNotNull(templatePath,"templatePath must not null!");
        CheckUtils.assertNotNull(classPackage,"classPackage must not null!");
        CheckUtils.assertNotNull(outputPath,"outputPath must not null!");
        CheckUtils.assertNotNull(classNameFormat,"classNameFormat must not null!");

        this.moduleType = moduleType;
        this.templatePath = templatePath;
        this.classPackage = classPackage;
        this.outputPath = outputPath;
        this.classNameFormat = classNameFormat;
    }

    public String getModuleType() {
        return moduleType;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public String getClassNameFormat() {
        return classNameFormat;
    }

    public String getOutputPath() {
        return outputPath;
    }
}
