package com.github.zhuyizhuo.generator.mybatis.generator.extension;

/**
 * class: FormatService <br>
 * description: 名称格式化 <br>
 * time: 2018/8/16 19:29
 *
 * @author yizhuo <br>
 * @since 1.0
 * @version 1.4.0
 */
public interface FormatService {

    /**
     * name 格式化
     * @param tableName 数据库表名称大写
     * @return 格式化后的名称
     */
    String format(String tableName);

}
