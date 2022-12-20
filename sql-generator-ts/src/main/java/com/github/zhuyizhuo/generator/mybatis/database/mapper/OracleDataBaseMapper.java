package com.github.zhuyizhuo.generator.mybatis.database.mapper;

import com.github.zhuyizhuo.generator.mybatis.database.pojo.ColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DataBaseInfo;
import com.github.zhuyizhuo.generator.mybatis.database.pojo.DbTableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  oracle 数据库 mapper
 * @author yizhuo
 * @version 1.0
 * time: 2018/7/27 20:55
 */
public interface OracleDataBaseMapper {
    /***
     * 根据表空间和表名查询所有的表信息
     * @param schema 查询参数对象
     * @return 所有表信息
     */
    List<DbTableInfo> getTableNameListBySchema(DataBaseInfo schema);

    /**
     * 根据表信息查询所有列信息
     * @param tableSchema 表空间
     * @param tableName 表名
     * @return 所有表和对应列信息
     */
    DbTableInfo getAllColumnsByTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    /**
     * 根据表信息查询主键信息
     * @param schema 查询主键参数对象
     * @return 所有主键信息
     */
    List<ColumnInfo> getPrimaryKeys(DbTableInfo schema);
}

