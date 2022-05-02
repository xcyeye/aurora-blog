package xyz.xcye.core.util;

import org.springframework.validation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Set;


public class ValidationUtils {

    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static boolean valid(Object validatingObject,Class<?>... groups) throws BindException {
        Set<ConstraintViolation<Object>> validates = validator.validate(validatingObject, groups);

        if (validates.size() > 0) {
            BindingResult bindingResult = new BeanPropertyBindingResult(validatingObject, validatingObject.getClass().getSimpleName());
            for (ConstraintViolation<Object> violation : validates) {
                //验证失败的消息
                String message = violation.getMessage();
                //错误的字段名称
                String field = violation.getPropertyPath().toString();
                ConstraintDescriptor<?> cd = violation.getConstraintDescriptor();
                //errorCode是由哪个验证器验证的
                String errorCode = determineErrorCode(cd);

                FieldError error = new FieldError(validatingObject.getClass().getSimpleName(),field,message);
                bindingResult.addError(error);

            }

            throw new BindException(bindingResult);
        }

        return true;
    }

    protected static String determineErrorCode(ConstraintDescriptor<?> descriptor) {
        return descriptor.getAnnotation().annotationType().getSimpleName();
    }


}
