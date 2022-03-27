package xyz.xcye.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.entity.ExceptionResultEntity;
import xyz.xcye.entity.R;
import xyz.xcye.enums.ResultStatusCode;
import xyz.xcye.util.SecurityResultHandler;

import java.nio.charset.StandardCharsets;


/**
 * 访问被拒绝时处理类
 * @author qsyyke
 */


@Slf4j
@Component
public class CustomAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        log.error("访问被拒绝时处理类 -CustomAccessDeniedHandler");

        return SecurityResultHandler.failure(exchange,denied,ResultStatusCode.PERMISSION_DENIED.getCode());
    }
}
