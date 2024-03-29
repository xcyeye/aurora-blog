package xyz.xcye.wg.exception.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.wg.util.SecurityResultHandler;

/**
 * 未登录访问需要认证的资源时处理类
 *
 * @author qsyyke
 */

@Component
public class AuroraAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return SecurityResultHandler.failure(exchange, ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
    }
}
