package xyz.xcye.service.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetByRedis {
	public enum TYPE{KEY, HASH};
	
	public static final int defaultExpriedSecond = -1;

	/**
	 * 存储类型
	 * @return
	 */
	TYPE type() default TYPE.HASH;

	/**
	 * redis存储时间 如果为-1，那么表示永久有效
	 * @return
	 */
	int expriedSecond() default defaultExpriedSecond;

	/**
	 * 自己指定一个key
	 * @return
	 */
	String key() default "";
}
