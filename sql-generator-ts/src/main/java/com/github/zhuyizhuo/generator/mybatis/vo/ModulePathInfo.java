package com.github.zhuyizhuo.generator.mybatis.vo;

/**
 * class: ModulePathInfo <br>
 * description: 模块信息 <br>
 * time: 2019/5/23
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class ModulePathInfo {
    /** 模块类型 */
    private String moduleType;
    /** 生成文件输出路径 */
    private String fileOutputPath;

    public ModulePathInfo(String moduleType, String fileOutputPath) {
        this.moduleType = moduleType;
        this.fileOutputPath = fileOutputPath;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getFileOutputPath() {
        return fileOutputPath;
    }

    public void setFileOutputPath(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
    }

}
