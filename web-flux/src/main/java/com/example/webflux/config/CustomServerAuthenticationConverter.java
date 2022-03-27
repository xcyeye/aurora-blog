package com.example.webflux.config;

import com.example.webflux.entity.MyAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomServerAuthenticationConverter implements ServerAuthenticationConverter {

    private String usernameParameter = "username";

    private String passwordParameter = "password";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {

        log.error("CustomServerAuthenticationConverter执行");
        HttpHeaders headers = exchange.getRequest().getHeaders();

        String tenant = headers.getFirst("_tenant");
        String host = headers.getHost().getHostName();

        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();

        List<String> username = queryParams.get(usernameParameter);
        List<String> password = queryParams.get(passwordParameter);

        return Mono.just(new MyAuthenticationToken(username.get(0),password.get(0), AuthorityUtils.commaSeparatedStringToAuthorityList("man")));

    }
}
