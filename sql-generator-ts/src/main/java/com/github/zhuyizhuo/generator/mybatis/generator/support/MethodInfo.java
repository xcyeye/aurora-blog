package com.github.zhuyizhuo.generator.mybatis.generator.support;

import com.github.zhuyizhuo.generator.mybatis.dto.MethodDescription;
import com.github.zhuyizhuo.generator.mybatis.enums.MethodEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.extension.FormatService;
import com.github.zhuyizhuo.generator.mybatis.vo.TableInfo;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法相关参数
 * @author yizhuo
 * @version 1.0
 * time: 2018/7/29 15:40
 */
public class MethodInfo {

    /** 表名 */
    private String tableName;
    /** 表名驼峰命名 */
    private String tableNameCamelCase;
    /**
     * method -> methodNameFormatService
     */
    private Map<MethodEnums,FormatService> methodNameFormatServiceMap;
    /**
     * 格式化全部方法名 service
     */
    private FormatService commonMethodNameFormatService;

    public MethodInfo() {
    }

    public MethodInfo(Map<MethodEnums, FormatService> methodNameFormatServiceMap, FormatService commonMethodNameFormatService) {
        this.methodNameFormatServiceMap = methodNameFormatServiceMap;
        this.commonMethodNameFormatService = commonMethodNameFormatService;
    }

    private String formatMethodName(MethodEnums method){
        FormatService formatService = null;
        if (this.methodNameFormatServiceMap != null){
            formatService = methodNameFormatServiceMap.get(method);
        }
        return formatService != null
                ? formatService.format(tableNameCamelCase)
                : MessageFormat.format(PropertiesUtils.getConfig(method.getMethodFormatKey()),
                                this.commonMethodNameFormatService != null
                                        ? commonMethodNameFormatService.format(tableName)
                                        :tableNameCamelCase);
    }

    public Map<String,MethodDescription> initMethodName(TableInfo tableInfo) {
        this.tableName = tableInfo.getTableName();
        this.tableNameCamelCase = tableInfo.getTableNameCamelCase();

        Map<String,MethodDescription> methodDescriptionMap = new ConcurrentHashMap<>();
        MethodDescription methodDescription;
        MethodEnums[] values = MethodEnums.values();
        for (int i = 0; i < values.length; i++) {
            if (MethodEnums.ALL_METHOD.equals(values[i])){
                continue;
            }
            methodDescription = new MethodDescription();
            methodDescription.setEnabled(getPropertiesDefaultTrue(values[i]));
            methodDescription.setMethodName(formatMethodName(values[i]));
            methodDescription.setComment(PropertiesUtils.getConfig(values[i].getMethodCommentKey()));
            methodDescription.addParams(methodDescription.new ParamDescription(tableName + " 参数对象"));
            methodDescriptionMap.put(values[i].toString(),methodDescription);
        }
        return methodDescriptionMap;
    }

    private boolean getPropertiesDefaultTrue(MethodEnums value) {
        return PropertiesUtils.getBooleanPropertiesDefaultTrue(value.getPropertiesEnabledKey());
    }

    public void setCommonMethodNameFormatService(FormatService commonMethodNameFormatService) {
        this.commonMethodNameFormatService = commonMethodNameFormatService;
    }

    public void setMethodNameFormatServiceMap(Map<MethodEnums, FormatService> methodNameFormatServiceMap) {
        this.methodNameFormatServiceMap = methodNameFormatServiceMap;
    }

}
