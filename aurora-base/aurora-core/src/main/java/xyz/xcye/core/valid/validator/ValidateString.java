package xyz.xcye.core.valid.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验字符串类型的字段，该字段不能为null，不能为空字符串，并且有长度限制
 * @author qsyyke
 */

@Target(ElementType.FIELD)
@Constraint(validatedBy = {ValidateStringValidator.class})
@Retention(RUNTIME)
public @interface ValidateString {

    int min() default 0;

    int max() default 500;

    String value();

    /**
     * {value}能够获取注解中的value=""值，实现是由el表达式实现的
     */
    String message() default "{value}不能是null和空值，并且长度必须在{min}到{max}之间";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
