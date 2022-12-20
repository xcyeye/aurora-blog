package com.github.zhuyizhuo.generator.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 此注解用来标识资源文件
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resource {

    /**
     * 标识资源文件配置
     * @return 资源文件的配置 key
     */
    String value();

}