package xyz.xcye.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.enums.ResultStatusCode;
import xyz.xcye.util.SecurityResultHandler;

/**
 * 未登录访问需要认证的资源时处理类
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.error("未登录访问需要认证的资源时处理类 -CustomAuthenticationEntryPoint");
        return SecurityResultHandler.failure(exchange,ex, ResultStatusCode.PERMISSION_USER_NOT_LOGIN.getCode());
    }
}
