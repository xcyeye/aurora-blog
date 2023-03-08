package xyz.xcye.core.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Status注解的校验器 用于校验只有1和0这两个值得字段，是否存在多余的值
 * @author qsyyke
 */


public class StatusValidator implements ConstraintValidator<Status, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        int temp = value;
        return temp == 1 || temp == 0;
    }

    @Override
    public void initialize(Status constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
