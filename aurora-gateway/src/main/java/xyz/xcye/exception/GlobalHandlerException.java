package xyz.xcye.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这是一个全局异常处理
 * @author qsyyke
 */

@Slf4j
@ControllerAdvice
@Component
public class GlobalHandlerException {

    @ResponseBody
    @ExceptionHandler(value = {ArithmeticException.class})
    public Object test(ArithmeticException exception) {
        log.error("全局异常处理执行");
        return "sdlkfjsdf";
    }
}
