package xyz.xcye.service.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 缓存获取注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetByRedis {
	public enum TYPE{KEY, HASH};
	
	public static final int defaultExpriedSecond = -1;
	
	/** 
	 * REDIS类型，目前只支持key和hash的存放方式
	 */
	TYPE type() default TYPE.HASH;
	
	/**
	 * 参数名，如果objField存在，则结合objField一起配合使用，这时的paramName必须为对象类型
	 */
	String[] paramName() default {};
	
	/**
	 * objField：对象中一个或多个属性，多个属性通过“_”构成缓存中的key
	 */
	String[] objField() default {};
	
	/**
	 * 超时时间，单位秒，-1表示永久存在
	 */
	int expriedSecond() default defaultExpriedSecond;
	
	/**
	 * key，可以单独制定key
	 */
	String key() default "";
	
	/**
	 * 参数名key，如果objFieldKey存在，则结合objFieldKey一起配合使用，这时的paramNameKey必须为对象类型
	 */
	String[] paramNameKey() default {};
	
	/**
	 * objFieldKey：对象中一个或多个属性，多个属性通过“_”构成缓存中的key
	 */
	String[] objFieldKey() default {};
}
