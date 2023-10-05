package xyz.xcye.service.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存获取注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PutToRedis {
    public enum TYPE {KEY, HASH}

    ;

    public static final int defaultExpriedSecond = -1;

    /**
     * REDIS类型，目前只支持key和hash的存放方式
     */
    TYPE type() default TYPE.HASH;

    /**
     * 超时时间，单位秒，-1表示永久存在
     */
    int expriedSecond() default defaultExpriedSecond;

    /**
     * key，可以单独制定key
     */
    String key() default "";
}
