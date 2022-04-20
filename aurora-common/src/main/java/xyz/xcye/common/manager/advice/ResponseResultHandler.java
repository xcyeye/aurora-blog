package xyz.xcye.common.manager.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.result.ExceptionResultEntity;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.enums.ResultStatusCode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 对所有响应的数据进行包装处理
 * @author qsyyke
 */

@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        //执行的方法
        Method method = methodParameter.getMethod();

        assert method != null;
        //判断是否存在ResponseResult注解
        boolean hasResponseResultAnnotation = method.isAnnotationPresent(ResponseResult.class);

        /**
         * 获取方法或者异常的返回值类型
         * 这里如果没有发生异常是通过注解进行判断，如果存在异常，因为做了全局处理，返回值是ExceptionResultEntity
         */
        Class<?> resultReturnType = method.getReturnType();

        if (hasResponseResultAnnotation) {
            //存在注解，没有发生异常，对结果封装
            return R.success(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getMessage(),responseBody);
        }

        if (responseBody instanceof ExceptionResultEntity) {
            //发生了异常，对结果进行封装
            ExceptionResultEntity exceptionResultEntity = (ExceptionResultEntity) responseBody;

            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("errorUrl",exceptionResultEntity.getErrorUrl());
            if (exceptionResultEntity.getData() != null) {
                errorMap.put("additionalData",exceptionResultEntity.getData());
            }

            return R.failure(exceptionResultEntity.getCode(), exceptionResultEntity.getMessage(),errorMap);
        }

        return responseBody;
    }
}
