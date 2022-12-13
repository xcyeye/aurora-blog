package com.github.zhuyizhuo.generator.mybatis.dto;

import com.github.zhuyizhuo.generator.utils.TypeConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * class: MybatisXmlDefinition <br>
 * description: mybatis 文件定义 <br>
 *
 * @author yizhuo <br>
 * @since 1.3.2
 */
public class MybatisXmlDefinition {
    /** 命名空间 */
    private String nameSpace;
    /** 结果集 */
    private ResultMapDefinition resultMap;
    /** xml 参数类型  -> method 关联? */
    private String parameterType;
    /** 列信息定义 */
    private List<MybatisColumnDefinition> columns;

    public MybatisXmlDefinition() {
        resultMap = new ResultMapDefinition();
        columns = new ArrayList<MybatisColumnDefinition>();
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public ResultMapDefinition getResultMap() {
        return resultMap;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public List<MybatisColumnDefinition> getColumns() {
        return columns;
    }

    public void addColumn(JavaColumnInfo columnInfo) {
        MybatisColumnDefinition mybatisColumnDefinition = new MybatisColumnDefinition(columnInfo);
        this.columns.add(mybatisColumnDefinition);
    }

    /**
     * 列定义
     */
    public class MybatisColumnDefinition{
        /** test 表达式 如果是 string 类型会判断是否空串  用于查询条件判断 */
        private String testNotBlankExpression;
        /** test 表达式 用于插入条件判断 */
        private String testNotNullExpression;
        /** 列名称 */
        private String columnName;
        /** java 字段名 */
        private String javaColumnName;
        /** java 字段类型 */
        private String javaDataType;
        /** mybatis xml 中 JDBC 类型  */
        private String jdbcType;
        /** mybatis xml中 parameterType */
        private String parameterType;
        /** 是否主键 */
        private boolean primaryKey;

        public MybatisColumnDefinition(JavaColumnInfo javaColumnInfo) {
            if (javaColumnInfo == null){
                throw new IllegalArgumentException("Init MybatisColumnDefinition error ! javaColumnInfo is null !");
            }
            this.javaColumnName = javaColumnInfo.getJavaColumnName();
            this.javaDataType = javaColumnInfo.getJavaDataType();
            this.columnName = javaColumnInfo.getColumnName();
            this.primaryKey = javaColumnInfo.isPrimaryKey();
            this.jdbcType = TypeConversion.type2JdbcType(javaColumnInfo.getDataType());
            this.parameterType = TypeConversion.getTypeByMap(TypeConversion.parameterTypeMap,javaColumnInfo.getJavaDataType());
            this.testNotNullExpression = javaColumnName + " != null";
            this.testNotBlankExpression = this.testNotNullExpression;
            if ("STRING".equalsIgnoreCase(javaDataType)){
               this.testNotBlankExpression += " and " +javaColumnName+ " != '' ";
            }
        }

        public String getTestNotBlankExpression() {
            return testNotBlankExpression;
        }

        public String getTestNotNullExpression() {
            return testNotNullExpression;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public String getParameterType() {
            return parameterType;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getJavaColumnName() {
            return javaColumnName;
        }

        public String getJavaDataType() {
            return javaDataType;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        @Override
        public String toString() {
            return "MybatisColumnDefinition{" +
                    "testNotBlankExpression='" + testNotBlankExpression + '\'' +
                    ", testNotNullExpression='" + testNotNullExpression + '\'' +
                    ", columnName='" + columnName + '\'' +
                    ", javaColumnName='" + javaColumnName + '\'' +
                    ", javaDataType='" + javaDataType + '\'' +
                    ", jdbcType='" + jdbcType + '\'' +
                    ", parameterType='" + parameterType + '\'' +
                    ", primaryKey=" + primaryKey +
                    '}';
        }
    }

    /**
     * ResultMap 定义
     */
    public class ResultMapDefinition {
        /** xml resultMap id */
        private String id;
        /** 类型 */
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "ResultMapDefinition{" +
                    "id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public void setResultMapId(String id){
        this.resultMap.setId(id);
    }

    public void setResultMapType(String type){
        this.resultMap.setType(type);
    }

    @Override
    public String toString() {
        return "MybatisXmlDefinition{" +
                "nameSpace='" + nameSpace + '\'' +
                ", resultMap=" + resultMap +
                ", parameterType='" + parameterType + '\'' +
                ", columns=" + columns +
                '}';
    }
}
