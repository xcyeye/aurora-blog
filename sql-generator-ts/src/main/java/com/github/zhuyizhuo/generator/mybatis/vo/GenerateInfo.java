package com.github.zhuyizhuo.generator.mybatis.vo;

import com.github.zhuyizhuo.generator.mybatis.constants.ConfigConstants;
import com.github.zhuyizhuo.generator.mybatis.convention.ClassCommentInfo;
import com.github.zhuyizhuo.generator.mybatis.dto.JavaClassDefinition;
import com.github.zhuyizhuo.generator.mybatis.dto.JavaColumnInfo;
import com.github.zhuyizhuo.generator.mybatis.dto.MethodDescription;
import com.github.zhuyizhuo.generator.mybatis.dto.MybatisXmlDefinition;
import com.github.zhuyizhuo.generator.mybatis.enums.ModuleEnums;
import com.github.zhuyizhuo.generator.utils.GeneratorStringUtils;
import com.github.zhuyizhuo.generator.utils.PropertiesUtils;

import java.util.List;
import java.util.Map;

/**
 * @author yizhuo
 * @version 1.0
 * time: 2018/7/29 17:44
 */
public class GenerateInfo {
    /** 类注释信息 */
    private ClassCommentInfo classCommentInfo;
    /** 分层信息 */
    private Map<String, JavaClassDefinition> javaClassDefinition;
    /** 方法信息 */
    private Map<String, MethodDescription> methodDescription;
    /** 表信息 */
    private TableInfo tableInfo;
    /** mybatis xml 定义*/
    private MybatisXmlDefinition mybatisXmlDefinition;

    public GenerateInfo() { }

    public GenerateInfo(ClassCommentInfo classCommentInfo, Map<String, JavaClassDefinition> javaClassDefinition, Map<String, MethodDescription> methodDescription, TableInfo tableInfo) {
        this.classCommentInfo = classCommentInfo;
        this.javaClassDefinition = javaClassDefinition;
        this.methodDescription = methodDescription;
        this.tableInfo = tableInfo;
    }

    public ClassCommentInfo getClassCommentInfo() {
        return classCommentInfo;
    }

    public void setClassCommentInfo(ClassCommentInfo classCommentInfo) {
        this.classCommentInfo = classCommentInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public MybatisXmlDefinition getMybatisXmlDefinition() {
        return mybatisXmlDefinition;
    }

    public void initXmlInfo() {
        mybatisXmlDefinition = new MybatisXmlDefinition();

        boolean useTypeAliases = PropertiesUtils.getBooleanPropertiesDefaultFalse(ConfigConstants.PARAMETER_TYPE_USE_TYPE_ALIASES);
        JavaClassDefinition pojoDefinition = javaClassDefinition.get(ModuleEnums.PO.toString());
        JavaClassDefinition mapperDefinition = javaClassDefinition.get(ModuleEnums.MAPPER.toString());

        String className = GeneratorStringUtils.firstLower(pojoDefinition.getClassName());
        mybatisXmlDefinition.setParameterType(useTypeAliases  ? className
                                                                : pojoDefinition.getFullPackage()+"."+pojoDefinition.getClassName());
        mybatisXmlDefinition.setNameSpace(mapperDefinition.getFullPackage()+"." +mapperDefinition.getClassName());
        mybatisXmlDefinition.setResultMapId(className+"ResultMap");
        mybatisXmlDefinition.setResultMapType(mybatisXmlDefinition.getParameterType());

        List<JavaColumnInfo> columns = tableInfo.getColumnLists();
        for (int i = 0; i < columns.size(); i++) {
            JavaColumnInfo javaColumnInfo = columns.get(i);
            mybatisXmlDefinition.addColumn(javaColumnInfo);
        }
    }

    public Map<String, MethodDescription> getMethodDescription() {
        return methodDescription;
    }

    public Map<String, JavaClassDefinition> getJavaClassDefinition() {
        return javaClassDefinition;
    }

    @Override
    public String toString() {
        return "GenerateInfo{" +
                "classCommentInfo=" + classCommentInfo +
                ", javaClassDefinition=" + javaClassDefinition +
                ", methodDescription=" + methodDescription +
                ", tableInfo=" + tableInfo +
                ", mybatisXmlDefinition=" + mybatisXmlDefinition +
                '}';
    }
}
