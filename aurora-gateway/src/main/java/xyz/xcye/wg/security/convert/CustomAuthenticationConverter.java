package xyz.xcye.wg.security.convert;



import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

/**
 * 自定义参数转换器
 * @author qsyyke
 */

@Component
public class CustomAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        //1. 从参数中获取用户名，密码，rememberMe

        //远程链接地址
        InetSocketAddress remoteAddress = request.getRemoteAddress();

        //访问uri
        URI requestURI = request.getURI();

        //请求头
        HttpHeaders headers = request.getHeaders();

        Flux<DataBuffer> body = request.getBody();

        MultiValueMap<String, String> queryParams = request.getQueryParams();

        String username = "";
        String password = "";
        //免登录天数
        String rememberMeDay = "";
        //从请求头中获取UserAgent
        String userAgent = "";

        //获取用户名
        if (queryParams.get("username") != null) {
            username = queryParams.get("username").get(0);
        }

        //获取密码
        if (queryParams.get("password") != null) {
            password = queryParams.get("password").get(0);
        }

        //获取免登录天数
        if (queryParams.get("remember-me-day") != null) {
            rememberMeDay = queryParams.get("remember-me-day").get(0);
        }

        if (headers.get("User-Agent") != null) {
            userAgent = headers.get("User-Agent").get(0);
        }

        //2. 不用管用户名或者密码错误或者为null
        //3. 返回一个Mono<Authentication>对象

        return Mono.just(new UsernamePasswordAuthenticationToken(username,password));

    }
}
