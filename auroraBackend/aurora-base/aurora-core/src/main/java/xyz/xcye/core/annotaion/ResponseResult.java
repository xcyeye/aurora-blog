package xyz.xcye.core.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这是一个用在controller的方法或者类上的注解，使用该注解的地方，返回的内容，都将被包装
 * @author qsyyke
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface ResponseResult {
    String value() default "发生异常了~~";
}
