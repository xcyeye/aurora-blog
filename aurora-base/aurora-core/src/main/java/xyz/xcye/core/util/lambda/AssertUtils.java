package xyz.xcye.core.util.lambda;

import org.springframework.util.StringUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 这是一个自定义断言
 * @author qsyyke
 * @date Created in 2022/5/4 18:06
 */


public class AssertUtils {
    public static <T,S,X extends Throwable> void isNotEqualThrow(T t, S s, Supplier<? extends X> exceptionSupplier) throws X {
        if (t != s) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void stateThrow(boolean expression, Supplier<? extends X> exceptionSupplier) throws X {
        if (!expression) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void ifNullThrow(Object value, Supplier<? extends X> exceptionSupplier) throws X {
        if (value == null) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void ifNoLengthThrow(String value, Supplier<? extends X> exceptionSupplier) throws X {
        if (!StringUtils.hasLength(value)) {
            throw exceptionSupplier.get();
        }
    }

    public static void hasLength(String value,Consumer<String> consumer) {
        if (StringUtils.hasLength(value)) {
            consumer.accept(value);
        }
    }
}
