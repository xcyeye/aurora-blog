package xyz.xcye.wg.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.wg.util.SecurityResultHandler;

/**
 * 未登录访问需要认证的资源时处理类
 * @author qsyyke
 */

@Component
public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return SecurityResultHandler.failure(exchange,ex, ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN.getCode());
    }
}
