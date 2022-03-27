package xyz.xcye.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.xcye.enums.ResultStatusCode;
import xyz.xcye.util.SecurityResultHandler;

/**
 * 这是自定义认证失败处理类
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        log.error("这是自定义认证失败处理类-CustomAuthenticationFailureHandler");
        //设置响应码
        webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.MULTI_STATUS);
        return SecurityResultHandler.failure(webFilterExchange.getExchange(),exception, ResultStatusCode.PERMISSION_AUTHORIZED_FAIL.getCode());
    }
}
