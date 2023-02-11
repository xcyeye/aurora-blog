package xyz.xcye.wg.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.wg.util.SecurityResultHandler;

/**
 * 用于网关的全局异常处理
 * @Order(-1)：优先级一定要比ResponseStatusExceptionHandler低
 */
@Slf4j
@Order(-1)
@Component
@RequiredArgsConstructor
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {



    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        LogUtils.logExceptionInfo((Exception) ex);
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        ResponseStatusCodeEnum responseStatusCodeEnum = null;
        if (ex instanceof NotFoundException) {
            // NotFoundException是服务实例未启动
            responseStatusCodeEnum = ResponseStatusCodeEnum.SERVICE_INSTANCE_NOT_FOUND;
        }else if (ex instanceof InvalidTokenException) {
            responseStatusCodeEnum = ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION;
        } else {
            responseStatusCodeEnum = ResponseStatusCodeEnum.UNKNOWN;
        }
        return SecurityResultHandler.failure(exchange, responseStatusCodeEnum);
    }

}