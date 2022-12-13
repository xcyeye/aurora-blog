package com.github.zhuyizhuo.generator.mybatis.generator.service.template.freemarker.impl;

import com.github.zhuyizhuo.generator.mybatis.enums.DbTypeEnums;
import com.github.zhuyizhuo.generator.mybatis.enums.ModuleEnums;
import com.github.zhuyizhuo.generator.mybatis.generator.service.template.freemarker.FreemarkerGenerateService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * class: MysqlGenerateImpl <br>
 * description: mysql 模板生成 <br>
 * time: 2019/5/28
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
public class MysqlGenerateImpl extends FreemarkerGenerateService {

    /**
     *  moduleTpye_hasPrivateKey -> templatePath
     */
    private Map<String,String> templatePathMap = new ConcurrentHashMap<>();

    public MysqlGenerateImpl() {
        addTemplatePath(ModuleEnums.XML,true, "/freemarker/template/xml/primary_key_mysql_mybatis_template.ftl");
        addTemplatePath(ModuleEnums.XML,false, "/freemarker/template/xml/no_primary_key_mysql_mybatis_template.ftl");

        addTemplatePath(ModuleEnums.MAPPER,true, "/freemarker/template/java/primary_key_mysql_mapper_template.ftl");
        addTemplatePath(ModuleEnums.MAPPER,false, "/freemarker/template/java/no_primary_key_mysql_mapper_template.ftl");
        
        addTemplatePath(ModuleEnums.SERVICE,true, "/freemarker/template/java/primary_key_mysql_service_template.ftl");

        addTemplatePath(ModuleEnums.PO,null, "/freemarker/template/java/javabean.ftl");
        addTemplatePath(ModuleEnums.POJO,null, "/freemarker/template/java/javabean_pojo.ftl");
        addTemplatePath(ModuleEnums.VO,null, "/freemarker/template/java/javabean_vo.ftl");
    }

    @Override
    protected DbTypeEnums getDbType() {
        return DbTypeEnums.MYSQL;
    }
}
