package xyz.xcye.aurora.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import xyz.xcye.core.entity.ExceptionResultEntity;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestControllerAdvice
@Component
public class AuroraGlobalExceptionHandler {

    /**
     * 参数类型校验失败
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ExceptionResultEntity bing(BindException e,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        //所有的字段验证错误信息
        List<Map<String,Object>> errorsList = new ArrayList<>();
        //错误集合
        BindingResult bindingResult = e.getBindingResult();

        //所有没有验证成功的字段集合
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            Map<String,Object> filedErrorMap = new HashMap<>();
            //验证失败的消息
            String message = fieldError.getDefaultMessage();
            //没有验证成功的字段
            String field = fieldError.getField();
            //此字段在哪个类对象中
            String objectName = fieldError.getObjectName();
            filedErrorMap.put("message",message);
            filedErrorMap.put("errorField",field);
            filedErrorMap.put("objectName",objectName);
            errorsList.add(filedErrorMap);
        });

        commonSetting(e, response);

        return new ExceptionResultEntity(
                ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode(),
                requestURI,errorsList);
    }

    /**
     * spring的参数缺失处理
     * @param exception
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResultEntity missingServletRequestParameterExceptionHandler(
            MissingServletRequestParameterException exception,
            HttpServletRequest request,
            HttpServletResponse response) {

        String requestURI = request.getRequestURI();
        commonSetting(exception, response);
        String parameterName = exception.getParameterName();
        String parameterType = exception.getParameterType();
        String message = ResponseStatusCodeEnum.PARAM_NOT_COMPLETE.getMessage() +
                " 缺失字段:" + parameterName + " 字段类型:" + parameterType;

        return new ExceptionResultEntity(message,requestURI, ResponseStatusCodeEnum.PARAM_NOT_COMPLETE.getCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ExceptionResultEntity nullPointerExceptionHandler(NullPointerException exception,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        commonSetting(exception, response);
        String msg = Optional.ofNullable(exception.getMessage()).orElse(ResponseStatusCodeEnum.EXCEPTION_NULL_POINTER.getMessage());
        return new ExceptionResultEntity(msg, requestURI, ResponseStatusCodeEnum.EXCEPTION_NULL_POINTER.getCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ExceptionResultEntity maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException exception,
                                                                       HttpServletRequest request,
                                                                       HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        commonSetting(exception, response);
        return new ExceptionResultEntity(ResponseStatusCodeEnum.EXCEPTION_FILE_EXCEED_MAX_SIZE.getMessage(),
                requestURI, ResponseStatusCodeEnum.EXCEPTION_FILE_EXCEED_MAX_SIZE.getCode());
    }

    @ExceptionHandler(AuroraException.class)
    public ExceptionResultEntity auroraExceptionHandler(AuroraException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        commonSetting(e, response);
        return new ExceptionResultEntity(e.getMessage(), requestURI, e.getStatusCode());
    }

    /**
     * 不知道的异常名称
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler
    public ExceptionResultEntity result(Exception e,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        commonSetting(e, response);
        return new ExceptionResultEntity(ResponseStatusCodeEnum.SYSTEM_ERROR.getMessage(), requestURI,
                ResponseStatusCodeEnum.SYSTEM_ERROR.getCode());
    }

    private void logExceptionInfo(Exception e) {
        log.error("错误消息: {}",e.getMessage(),e);
    }

    private void commonSetting(Exception e, HttpServletResponse response) {
        // 设置响应码，否则出现异常，seata不会回滚
        response.setStatus(500);
        logExceptionInfo(e);
    }
}

