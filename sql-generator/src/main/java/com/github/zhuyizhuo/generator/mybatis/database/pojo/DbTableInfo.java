package com.github.zhuyizhuo.generator.mybatis.database.pojo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 数据库实体
 * 2018-7-27 21:15:01
 * @author yizhuo
 * @version 1.0
 */
public class DbTableInfo {
	/** 数据库名称 */
	private String tableSchema;
	/** 表名 */
	private String tableName;
	/** 表注释 */
	private String tableComment;
	/** 表字段 */
	private List<ColumnInfo> columnLists = Lists.newArrayList();

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<ColumnInfo> getColumnLists() {
		return columnLists;
	}

	public void setColumnLists(List<ColumnInfo> columnLists) {
		this.columnLists = columnLists;
	}

	@Override
	public String toString() {
		return "DbTableInfo{" +
				"tableSchema='" + tableSchema + '\'' +
				", tableName='" + tableName + '\'' +
				", tableComment='" + tableComment + '\'' +
				columnLists
				+'}';
	}
}
