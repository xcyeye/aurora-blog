package xyz.xcye.file.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.xcye.common.entity.result.ExceptionResultEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qsyyke
 */


@Slf4j
@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    /**
     * 不知道的异常名称
     * @param e
     * @param request
     * @return
     */
    /*@ExceptionHandler
    public ExceptionResultEntity result(Exception e, HttpServletRequest request) {
        log.error("发生异常：message:{},uri:{},消息信息:{}",e.getMessage(),request.getRequestURI(),e.getStackTrace());
        String requestURI = request.getRequestURI();
        return new ExceptionResultEntity(e.getMessage(),requestURI,ResponseStatusCodeEnum.UNKNOWN.getCode());
    }

    *//**
     * 参数类型校验失败
     * @param e
     * @param request
     * @return
     *//*
    @ExceptionHandler(BindException.class)
    public ExceptionResultEntity bing(BindException e,HttpServletRequest request) {
        log.error("发生异常：message:{},uri:{},消息信息:{}",e.getMessage(),request.getRequestURI(),e.getStackTrace());
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

        return new ExceptionResultEntity(
                ResponseStatusCodeEnum.PARAM_IS_INVALID.getMessage(),
                ResponseStatusCodeEnum.PARAM_IS_INVALID.getCode(),
                requestURI,errorsList);
    }*/


    /**
     * 如果操作文件发生异常，则抛出此异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(CustomFileException.class)
    public ExceptionResultEntity createFileException(CustomFileException e,HttpServletRequest request) {
        log.error("发生异常：message:{},uri:{},消息信息:{}",e.getMessage(),request.getRequestURI(),e.getStackTrace());
        return new ExceptionResultEntity(e.getMessage(),request.getRequestURI(),e.getCode());
    }
}
