package com.github.zhuyizhuo.generator.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 此注解用来标识资源配置
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     * @return 对应 value 的配置 key
     */
    String value();

}