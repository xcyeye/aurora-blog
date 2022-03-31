package xyz.xcye.springtest.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author qsyyke
 */


@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Object test(Exception e) {
        System.out.println(e.getMessage());
        return e.getMessage();
    }
}
