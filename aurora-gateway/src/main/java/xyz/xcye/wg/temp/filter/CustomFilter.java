package xyz.xcye.wg.temp.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author qsyyke
 */


/*@Component
@Slf4j
public class CustomFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("CustomFilter执行");

        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap("sdlkfhj".getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}*/

