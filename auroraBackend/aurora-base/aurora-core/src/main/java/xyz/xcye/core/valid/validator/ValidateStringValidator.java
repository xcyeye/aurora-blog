package xyz.xcye.core.valid.validator;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;

/**
 * ValidateString的校验器，校验字段内容包括，是否是null，空，并且字段值有长度限制长度
 * @author qsyyke
 */


public class ValidateStringValidator implements ConstraintValidator<ValidateString, CharSequence> {

    private static final Log LOG = LoggerFactory.make( MethodHandles.lookup() );

    private int min;
    private int max;

    @Override
    public void initialize(ValidateString parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        //判断是否是空字符串
        if (!(value.toString().trim().length() > 0)) {
            return false;
        }

        //判断长度 有长度
        int length = value.length();
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if ( min < 0 ) {
            throw LOG.getMinCannotBeNegativeException();
        }
        if ( max < 0 ) {
            throw LOG.getMaxCannotBeNegativeException();
        }
        if ( max < min ) {
            throw LOG.getLengthCannotBeNegativeException();
        }
    }
}
