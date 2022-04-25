package xyz.xcye.springtest.exception;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qsyyke
 */


@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Object test(Exception e) {
        System.out.println(e.getMessage());
        return "sdfklhj";
    }
}
