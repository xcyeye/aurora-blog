package com.github.zhuyizhuo.generator.mybatis.vo;

import com.github.zhuyizhuo.generator.mybatis.database.pojo.ColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.dto.JavaColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.enums.DbTypeEnums;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * class: TableInfo <br>
 * description: 生成模板所用对象 <br>
 * time: 2018/8/3 19:56
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class TableInfo {
    /** 数据库类型 */
    private DbTypeEnums dbType;
    /** 数据库名称 */
    private String tableSchema;
    /** 表名 */
    private String tableName;
    /** 表注释 */
    private String tableComment;
    /** 表名转驼峰 首字母大写 */
    private String tableNameCamelCase;
    /** 导入的类路径 */
    private LinkedHashSet<String> importPackages;
    /** 表所有字段 */
    private List<JavaColumnInfo> columnLists;
    /** 主键字段 */
    private List<JavaColumnInfo> primaryKeyColumns;
    /** 是否有主键 */
    private boolean hasPrimaryKey;
    /** 是否单个主键 */
    private boolean singlePrimaryKey;

    public TableInfo() {
        importPackages = new LinkedHashSet<>();
        columnLists = new ArrayList<>();
        primaryKeyColumns = new ArrayList<>();
        hasPrimaryKey = false;
        singlePrimaryKey = false;
    }

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

    public List<JavaColumnInfo> getColumnLists() {
        return columnLists;
    }

    public void addJavaColumnInfo(JavaColumnInfo javaColumnInfo) {
        this.columnLists.add(javaColumnInfo);
    }

    public String getTableNameCamelCase() {
        return tableNameCamelCase;
    }

    public void setTableNameCamelCase(String tableNameCamelCase) {
        this.tableNameCamelCase = tableNameCamelCase;
    }

    public LinkedHashSet<String> getImportPackages() {
        return importPackages;
    }

    public void addImportPackage(String importPackage) {
        if (importPackage == null){
            return;
        }
        this.importPackages.add(importPackage);
    }

    public void addPrimaryKeyColumn(List<ColumnInfo> keyName) {
        if (keyName == null || keyName.size() == 0){
            return;
        }
        for (int i = 0; i < keyName.size(); i++) {
            ColumnInfo columnInfo = keyName.get(i);
            String columnName = columnInfo.getColumnName();
            if (GeneratorStringUtils.isNotBlank(columnName)){
                for (int j = 0; j < columnLists.size(); j++) {
                    JavaColumnInfo javaColumnInfo = columnLists.get(j);
                    if (columnName.equalsIgnoreCase(javaColumnInfo.getColumnName())){
                        javaColumnInfo.setPrimaryKey(true);
                        this.primaryKeyColumns.add(javaColumnInfo);
                        break;
                    }
                }
            }
        }

        this.hasPrimaryKey = true;
        this.singlePrimaryKey = primaryKeyColumns.size() == 1;
    }

    public List<JavaColumnInfo> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public boolean isHasPrimaryKey() {
        return hasPrimaryKey;
    }

    public void setHasPrimaryKey(boolean hasPrimaryKey) {
        this.hasPrimaryKey = hasPrimaryKey;
    }

    public boolean isSinglePrimaryKey() {
        return singlePrimaryKey;
    }

    public void setSinglePrimaryKey(boolean singlePrimaryKey) {
        this.singlePrimaryKey = singlePrimaryKey;
    }

    public DbTypeEnums getDbType() {
        return dbType;
    }

    public void setDbType(DbTypeEnums dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "dbType=" + dbType +
                ", tableSchema='" + tableSchema + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", tableNameCamelCase='" + tableNameCamelCase + '\'' +
                ", importPackages=" + importPackages +
                ", columnLists=" + columnLists +
                ", primaryKeyColumns=" + primaryKeyColumns +
                ", hasPrimaryKey=" + hasPrimaryKey +
                ", singlePrimaryKey=" + singlePrimaryKey +
                '}';
    }
}