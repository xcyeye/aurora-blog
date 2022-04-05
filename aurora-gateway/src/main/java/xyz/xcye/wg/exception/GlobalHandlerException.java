package xyz.xcye.wg.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.enums.ResultStatusCode;

/**
 * 这是一个全局异常处理
 * @author qsyyke
 */

@Slf4j
@ControllerAdvice
@Component
public class GlobalHandlerException {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public Object test(Exception exception) {
        log.error("发生异常了: 时间{},异常信息: {}",System.currentTimeMillis(),exception.getMessage());

        return R.failure(ResultStatusCode.UNKNOWN.getCode(), exception.getMessage(),exception.getStackTrace());
    }
}
