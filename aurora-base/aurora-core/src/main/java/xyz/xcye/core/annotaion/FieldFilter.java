package xyz.xcye.core.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qsyyke
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
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
