package xyz.xcye.core.valid.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 所有删除，展示，发送等状态的字段验证，只有两个值（0,1）得判断
 * @author qsyyke
 */

@Target({  FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { StatusValidator.class })
public @interface Status {
    String message() default "{value}状态的值只能是0或者1";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * 是删除，展示，还是发送等状态
     */
    String value();
}
