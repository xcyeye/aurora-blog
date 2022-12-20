package com.github.zhuyizhuo.generator.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 此注解用来标识 字段 方法属性 参数 可为空
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Nullable {
}

