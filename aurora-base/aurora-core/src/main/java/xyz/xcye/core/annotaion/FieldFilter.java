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
     * 需要排除的字段
     */
    String[] excludeFields();
}
