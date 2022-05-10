package xyz.xcye.wg.exception.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.wg.util.SecurityResultHandler;


/**
 * 访问被拒绝时处理类
 * @author qsyyke
 */


@Component
public class AuroraAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return SecurityResultHandler.failure(exchange,
                ResponseStatusCodeEnum.PERMISSION_DENIED.getMessage(),
                ResponseStatusCodeEnum.PERMISSION_DENIED.getCode());
    }
}
