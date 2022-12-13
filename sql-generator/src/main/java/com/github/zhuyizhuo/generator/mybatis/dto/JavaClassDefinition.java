package com.github.zhuyizhuo.generator.mybatis.dto;

/**
 * class: JavaClassDefinition <br>
 * description: java 类定义 <br>
 * time: 2019/5/23
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class JavaClassDefinition {
    /** 包路径 */
    private String fullPackage;
    /** 类名 */
    private String className;
    /** 类名驼峰 */
    private String classNameCamelCase;

    public JavaClassDefinition() {
    }

    public JavaClassDefinition(String fullPackage, String className, String classNameCamelCase) {
        this.fullPackage = fullPackage;
        this.className = className;
        this.classNameCamelCase = classNameCamelCase;
    }

    public String getFullPackage() {
        return fullPackage;
    }

    public void setFullPackage(String fullPackage) {
        this.fullPackage = fullPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameCamelCase() {
		return classNameCamelCase;
	}

	public void setClassNameCamelCase(String classNameCamelCase) {
		this.classNameCamelCase = classNameCamelCase;
	}

	@Override
    public String toString() {
        return "JavaClassDefinition{" +
                "fullPackage='" + fullPackage + '\'' +
                ", className='" + className + '\'' +
                ", classNameCamelCase='" + classNameCamelCase + '\'' +
                '}';
    }
}
