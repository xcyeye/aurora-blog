package com.github.zhuyizhuo.generator.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * class: SqlSessionUtils <br>
 * description: 获取 SqlSession <br>
 * time: 2018/7/30 12:34
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class SqlSessionUtils {

    /**
     *  相对路径加载配置文件
     * @return SqlSession instance
     */
    public static SqlSession getSqlSession() throws RuntimeException {
        try {
            //配置文件
            String resource = "mybatis/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, PropertiesUtils.proInfo);
            return sqlSessionFactory.openSession();
        } catch (Exception e){
            throw new RuntimeException("SqlSessionUtils.getSqlSession Exception",e);
        }
    }

}
