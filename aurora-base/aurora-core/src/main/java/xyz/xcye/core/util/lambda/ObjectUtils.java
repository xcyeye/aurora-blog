package xyz.xcye.core.util.lambda;

import java.util.function.Consumer;

/**
 * @author qsyyke
 * @date Created in 2022/5/5 08:48
 */


public class ObjectUtils {

    public static <T> void ifNotNull(T t, Consumer<T> consumer) {
        if (t != null) {
            consumer.accept(t);
        }
    }
}
