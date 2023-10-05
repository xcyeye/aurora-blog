package xyz.xcye.core.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解是一个自定义的字段过滤注解，在返回json数据的时候，如果你让对象中的某个属性，被返回
 * 则可以在Controller的方法上，使用该注解
 *
 * @author qsyyke
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface FieldFilter {
    /**
     * 过滤哪个类
     */
    Class<?> value();

    /**
     * 需要过滤的字段
     */
    String[] excludeFields();

    /**
     * 哪些角色忽略，也就是如果我们设置过滤某个字段，但是对于某个角色，是不过滤的，比如超级管理员就可以看到所有信息
     */
    String[] ignoreRole() default "";
}
