package xyz.xcye.wg.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * 这是sercurity的工具类
 * @author qsyyke
 */


public class SecurityUtil {

    public static String getRequestUri(ServerWebExchange exchange) {

        return exchange.getRequest().getURI().getPath();
    }
}
