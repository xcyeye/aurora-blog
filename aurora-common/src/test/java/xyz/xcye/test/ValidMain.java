package xyz.xcye.test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author qsyyke
 */


public class ValidMain {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(1);
        userInfo.setName("");
        List<String> valid = ValidUtil.valid(userInfo);
        System.out.println(valid);
    }
}

class ValidUtil {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static List<String> valid(UserInfo userInfo, Class<?>... groups) {
        Set<ConstraintViolation<UserInfo>> set = validator.validate(userInfo, groups);

        List<String> list = set.stream().map(
                v -> "属性: " +
                        v.getPropertyPath() + "，属性的值: " +
                        v.getInvalidValue() + "，校验不通过的提示信息: " +
                        v.getMessage()).collect(Collectors.toList());
        return list;
    }
}
