package xyz.xcye.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.xcye.util.SecurityResultHandler;

/**
 * 自定义登录成功处理类
 * @author qsyyke
 */


@Component
public class CustomAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return SecurityResultHandler.success(webFilterExchange.getExchange(),authentication);
    }
}
