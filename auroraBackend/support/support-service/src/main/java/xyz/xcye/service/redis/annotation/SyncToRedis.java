package xyz.xcye.service.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存同步注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncToRedis {
    public enum TYPE {KEY, HASH}

    ;

    /**
     * REDIS类型，目前只支持key和hash的存放方式
     */
    TYPE type() default TYPE.HASH;

    /**
     * key
     */
    String key() default "";
}
