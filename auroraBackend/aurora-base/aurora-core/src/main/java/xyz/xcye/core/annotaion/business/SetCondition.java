package xyz.xcye.core.annotaion.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface SetCondition {

    String uid() default "";

    String otherField() default "";

    String otherUid() default "";

    String show() default "";

    String status() default "";

    String delete() default "";

    String orderBy() default "";

    String keyword() default "";
}
