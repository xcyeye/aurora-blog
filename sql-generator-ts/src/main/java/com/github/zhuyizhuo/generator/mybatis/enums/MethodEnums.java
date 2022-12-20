package com.github.zhuyizhuo.generator.mybatis.enums;

/**
 * 方法枚举
 *
 * @author yizhuo <br>
 * @since 1.4.0
 * @version 1.4.0
 */
public enum MethodEnums {
    /** 新增方法 */
    INSERT("generate.java.method.insert.","enabled","comment","name-format"),
    /** 批量新增方法*/
    BATCH_INSERT("generate.java.method.batch-insert.","enabled","comment","name-format"),
    /** 删除方法 */
    DELETE_BY_WHERE("generate.java.method.delete-by-where.","enabled","comment","name-format"),
    /** 根据主键删除方法 */
    DELETE_BY_PRIMARY_KEY("generate.java.method.delete-by-primary-key.","enabled","comment","name-format"),
    /** 根据主键更新方法 */
    UPDATE_BY_PRIMARY_KEY("generate.java.method.update-by-primary-key.","enabled","comment","name-format"),
    /** 查询方法 */
    QUERY_BY_WHERE("generate.java.method.query-by-where.","enabled","comment","name-format"),
    /** 根据主键查询 */
    QUERY_BY_PRIMARY_KEY("generate.java.method.query-by-primary-key.","enabled","comment","name-format"),
    /** 查询总数方法 */
    COUNT_BY_WHERE("generate.java.method.count-by-where.","enabled","comment","name-format"),
    /** 查询一条记录 */
    QUERY_ONE("generate.java.method.query-one.","enabled","comment","name-format"),
    /** 所有方法 */
    ALL_METHOD
    ;

    private String propertiesBaseKey;
    /** 配置是否生成方法的 key */
    private String propertiesEnabledKey;
    /** 方法注释配置 key */
    private String methodCommentKey;
    /** 默认格式化 */
    private String methodFormatKey;

    MethodEnums() {
    }

    MethodEnums(String propertiesBaseKey, String propertiesEnabledKey, String methodCommentKey, String methodFormatKey) {
        this.propertiesBaseKey = propertiesBaseKey;
        this.propertiesEnabledKey = propertiesEnabledKey;
        this.methodCommentKey = methodCommentKey;
        this.methodFormatKey = methodFormatKey;
    }

    public String getPropertiesEnabledKey() {
        return propertiesBaseKey + propertiesEnabledKey;
    }

    public String getMethodCommentKey() {
        return propertiesBaseKey + methodCommentKey;
    }

    public String getMethodFormatKey() {
        return propertiesBaseKey + methodFormatKey;
    }
}