package xyz.xcye.core.enums;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author qsyyke
 * @date Created in 2022/5/15 08:44
 */

public enum SensitiveStrategyEnum {
    /**
     * 手机号码
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")),
    /**
     * 地址，将区后面的部分，使用******替换
     */
    ADDRESS(s -> s.replaceAll("(\\S+)区(\\S+)", "$1******")),

    /**
     * 身份证，将5到14位的数字，使用******替换
     */
    ID_CARD(s -> s.replaceAll("(\\d){4}\\d{10}(\\w{4})", "$1******$2")),

    IP(s -> s.replaceAll("(\\d{1,4})\\.(\\d{1,4})\\.(\\d{1,4})\\.(\\d{1,4})","$1****$4"));



    private final Function<String, String> desensitizer;

    SensitiveStrategyEnum(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    /**
     * 获取一个Function
     */
    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
