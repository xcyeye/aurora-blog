package com.github.zhuyizhuo.generator.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 此注解用来标识约定类
 * 如果类中属性没有配置则赋值默认值
 *
 * @author yizhuo <br>
 * @since 1.4.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CoventionClass {
}

