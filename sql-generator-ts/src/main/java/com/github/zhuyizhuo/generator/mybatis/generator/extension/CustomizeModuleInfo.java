package com.github.zhuyizhuo.generator.mybatis.generator.extension;

import com.github.zhuyizhuo.generator.utils.CheckUtils;

/**
 * class: CustomizeModuleInfo <br>
 * description: 自定义模块 <br>
 * time: 2019-5-29
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class CustomizeModuleInfo {
    /** 模块 例如 controller service 等 */
    private String moduleType;
    /** 模板路径 使用模板生成时 需要传入模板路径 */
    private String templatePath;
    /**
     *  文件输出路径模板 如果单表生成 也可直接写为真实文件输出全路径
     *
     *  @See java.text.MessageFormat#format(java.lang.String, java.lang.Object...)
     *  outPutFullPathFormatPattern 为 第一个参数
     *  文件名为第二个参数
     */
    private String outPutFullPathFormatPattern;
    /**
     *  文件名格式化 例如 {0}Mapper  适用于批量及单表生成
     *  或直接指定文件名 例如 UserMapper 仅适用于单表生成
     *
     * @See java.text.MessageFormat#format(java.lang.String, java.lang.Object...)
     * fileNameFormatPattern 为第一个参数
     * 表名转驼峰命名 为第二个参数
     *
     * 也可使用 GeneratorBuilder.addModuleNameFormat(moduleType,moduleNameFormatService)
     * 自定义本 moduleType 对应的文件名格式化 service
     *
     * 自定义 service 之后,本配置自动失效
     */
    private String fileNameFormatPattern;

    public CustomizeModuleInfo(String moduleType, String templatePath, String outPutFullPathFormatPattern, String fileNameFormatPattern) {
        CheckUtils.assertNotNull(moduleType,"moduleType must not null!");
        CheckUtils.assertNotNull(templatePath,"templatePath must not null!");
        CheckUtils.assertNotNull(outPutFullPathFormatPattern,"outPutFullPathFormatPattern must not null!");
        CheckUtils.assertNotNull(fileNameFormatPattern,"fileNameFormatPattern must not null!");

        this.moduleType = moduleType;
        this.templatePath = templatePath;
        this.outPutFullPathFormatPattern = outPutFullPathFormatPattern;
        this.fileNameFormatPattern = fileNameFormatPattern;
    }

    public String getModuleType() {
        return moduleType;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public String getOutPutFullPathFormatPattern() {
        return outPutFullPathFormatPattern;
    }

    public String getFileNameFormatPattern() {
        return fileNameFormatPattern;
    }

}
