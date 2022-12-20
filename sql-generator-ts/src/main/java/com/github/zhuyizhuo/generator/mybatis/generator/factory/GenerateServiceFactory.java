package com.github.zhuyizhuo.generator.mybatis.generator.factory;

import com.github.zhuyizhuo.generator.mybatis.constants.ConfigConstants;
import com.github.zhuyizhuo.generator.mybatis.enums.DbTypeEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.service.GenerateService;
import com.github.zhuyizhuo.generator.mybatis.generator.service.template.freemarker.impl.MysqlGenerateImpl;
import com.github.zhuyizhuo.generator.mybatis.generator.service.template.freemarker.impl.OracleGenerateImpl;
import com.github.zhuyizhuo.generator.utils.CheckUtils;
import com.github.zhuyizhuo.generator.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class: GenerateServiceFactory <br>
 * description: 生成器工厂 <br>
 * time: 2019/5/28
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class GenerateServiceFactory {

    private static Map<String,GenerateService> serviceMap = new ConcurrentHashMap<>();

    static{
        serviceMap.put(DbTypeEnums.MYSQL.toString(), new MysqlGenerateImpl());
        serviceMap.put(DbTypeEnums.ORACLE.toString(), new OracleGenerateImpl());
    }

    public static GenerateService getGenerateService() {
        String dbType = CheckUtils.checkDBType();
        GenerateService generateService = serviceMap.get(dbType);
        if (generateService == null){
            String errorMsg =  ConfigConstants.DB_TYPE + "配置类型不支持,所支持类型请参照 DbTypeEnums.java";
            LogUtils.printErrInfo(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        return generateService;
    }
}
