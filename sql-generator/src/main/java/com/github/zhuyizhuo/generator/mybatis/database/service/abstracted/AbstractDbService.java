package com.github.zhuyizhuo.generator.mybatis.database.service.abstracted;

import com.github.zhuyizhuo.generator.mybatis.constants.ConfigConstants;
import com.github.zhuyizhuo.generator.mybatis.convention.ClassCommentInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.ColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DataBaseInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DbTableInfo;
import com.github.zhuyizhuo.generator.mybatis.database.service.DbService;
import com.github.zhuyizhuo.generator.mybatis.dto.JavaColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.vo.TableInfo;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;
import com.github.zhuyizhuo.generator.utils.TypeConversion;
import com.google.common.base.Splitter;

import java.util.List;

/**
 * class: AbstractDbService <br>
 * description: 关系型数据库抽象类 <br>
 * time: 2018/7/30 13:12
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public abstract class AbstractDbService implements DbService {

    /** 数据库表名分隔符 */
    public static String tableRegex = "";
    /** 字段分隔符 例如 order_no 的分隔符为 _ */
    public static String fieldRegex = "";

    protected DataBaseInfo getDataBaseInfo() {
        DataBaseInfo tableInfo = new DataBaseInfo();
        tableInfo.setTableSchema(getTableSchema());
        tableInfo.setTableNames(getTables());
        return tableInfo;
    }

    protected String getTableSchema() {
        return PropertiesUtils.getProperties(ConfigConstants.TABLE_SCHEMA);
    }

    protected List<String> getTables() {
        String includeTableName = PropertiesUtils.getProperties(ConfigConstants.GENERATE_TABLES_NAME);
        if (GeneratorStringUtils.isNotBlank(includeTableName)){
            return Splitter.on(",").splitToList(includeTableName);
        }
        return null;
    }

    /**
     * 将表信息处理成 java 信息
     * @param sourceTableInfo 数据库表信息
     * @param targetTableInfo 生成器所需的 java 对象
     */
    protected void setTableInfo(DbTableInfo sourceTableInfo, TableInfo targetTableInfo) {

        targetTableInfo.setTableName(sourceTableInfo.getTableName());
        targetTableInfo.setTableSchema(sourceTableInfo.getTableSchema());
        if (GeneratorStringUtils.isBlank(sourceTableInfo.getTableComment())){
            targetTableInfo.setTableComment(ClassCommentInfo.tableComment);
        } else {
            targetTableInfo.setTableComment(sourceTableInfo.getTableComment());
        }
        List<ColumnInfo> columnLists = sourceTableInfo.getColumnLists();
        JavaColumnInfo javaColumnInfo;
        for (int i = 0; i < columnLists.size(); i++) {
            ColumnInfo columnInfo = columnLists.get(i);
            javaColumnInfo = new JavaColumnInfo();
            javaColumnInfo.setDataType(getDataType(columnInfo.getDataType()));
            javaColumnInfo.setColumnName(columnInfo.getColumnName());
            javaColumnInfo.setColumnComment(replaceEnter(columnInfo.getColumnComment()));
            javaColumnInfo.setJavaColumnName(GeneratorStringUtils
                    .changeColmName2CamelFirstLower(columnInfo.getColumnName(), fieldRegex));
            javaColumnInfo.setJavaDataType(getJavaDataType(columnInfo));
            /** 设置类全路径 java.lang包下的类不需要import */
            javaColumnInfo.setJavaDataTypeFullPath(TypeConversion.javaDataTypeFullPathMap.get(javaColumnInfo.getJavaDataType()));
            targetTableInfo.addJavaColumnInfo(javaColumnInfo);
            targetTableInfo.addImportPackage(javaColumnInfo.getJavaDataTypeFullPath());
        }
    }

    protected String getDataType(String dataType) {
        if (GeneratorStringUtils.isNotBlank(dataType) && dataType.contains("TIMESTAMP")){
            return "TIMESTAMP";
        }
        return dataType;
    }

    protected abstract String getJavaDataType(ColumnInfo columnInfo);

    /**
     * 备注去除回车换行
     * @param columnComment 字段备注
     * @return 去除回车换行后返回
     */
    protected String replaceEnter(String columnComment) {
        if (GeneratorStringUtils.isBlank(columnComment)) {
            return "";
        }
        return columnComment.replaceAll("\r"," ").replaceAll("\n"," ").replaceAll("\r\n"," ");
    }

    /**
     * 数据库表名根据分隔符转为驼峰命名
     * @param tableName 数据库表名
     * @return 驼峰命名
     */
    protected String changeTableNameCamelCase(String tableName) {
        return GeneratorStringUtils.changeTableName2CamelFirstUpper(tableName, tableRegex);
    }
}
