package com.github.zhuyizhuo.generator.utils;

import com.github.zhuyizhuo.generator.mybatis.constants.ConfigConstants;

/**
 * class: CheckUtils <br>
 * description: 校验工具 <br>
 * time: 2018/8/20 13:01
 *
 * @author yizhuo <br>
 * @since 1.3.0
 */
public class CheckUtils {
    public static final String[] dbConfig = {ConfigConstants.URL,ConfigConstants.DRIVER,ConfigConstants.USERNAME,ConfigConstants.PASSWORD,ConfigConstants.TABLE_SCHEMA};

    public static String checkDBType() {
        String dbType = PropertiesUtils.getProperties(ConfigConstants.DB_TYPE);
        if (GeneratorStringUtils.isBlank(dbType)){
            String errorMsg = "未指定数据库类型:" + ConfigConstants.DB_TYPE + ", 值列表请参照 DbTypeEnums.java";
            throw new IllegalArgumentException(errorMsg);
        }
        return dbType.toUpperCase();
    }

    public static void checkNeedConfig() {
        StringBuffer errorMsg = new StringBuffer();
        for (int i = 0; i < dbConfig.length; i++) {
            if (isBlank(PropertiesUtils.getProperties(dbConfig[i]))){
                errorMsg.append("未配置 " + dbConfig[i] + "  \n");
            }
        }
        if (errorMsg.length() > 0){
            throw new IllegalArgumentException(errorMsg.toString());
        }
    }

    private static boolean isBlank(String properties) {
        if (GeneratorStringUtils.isBlank(properties)) {
            return true;
        }
        return false;
    }

    public static void assertNotNull(Object moduleType, String errorMsg) throws IllegalArgumentException {
        if (moduleType == null){
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static void assertNotNull(String moduleType, String errorMsg) throws IllegalArgumentException {
        if(GeneratorStringUtils.isBlank(moduleType.toString())){
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
