package com.github.zhuyizhuo.generator.mybatis.dto;

import com.github.zhuyizhuo.generator.mybatis.database.pojo.ColumnInfo;

/**
 * class: JavaColumnInfo <br>
 * description: java字段实体 <br>
 * time: 2018/7/29 16:06
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class JavaColumnInfo extends ColumnInfo {
	/** 转为驼峰命名后的字段名 */
	private String javaColumnName;
	/** java字段类型 */
	private String javaDataType;
	/** java字段类型全称 */
	private String javaDataTypeFullPath;

	public JavaColumnInfo() {
	}

	public String getJavaColumnName() {
		return javaColumnName;
	}

	public void setJavaColumnName(String javaColumnName) {
		this.javaColumnName = javaColumnName;
	}

	public String getJavaDataType() {
		return javaDataType;
	}

	public void setJavaDataType(String javaDataType) {
		this.javaDataType = javaDataType;
	}

	public String getJavaDataTypeFullPath() {
		return javaDataTypeFullPath;
	}

	public void setJavaDataTypeFullPath(String javaDataTypeFullPath) {
		this.javaDataTypeFullPath = javaDataTypeFullPath;
	}

	@Override
	public String toString() {
		return "JavaColumnInfo{" +
				"javaColumnName='" + javaColumnName + '\'' +
				", javaDataType='" + javaDataType + '\'' +
				", javaDataTypeFullPath='" + javaDataTypeFullPath + '\'' +
				'}';
	}
}
