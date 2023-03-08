package xyz.xcye.wg.util;

import org.springframework.web.server.ServerWebExchange;

/**
 * 这是sercurity的工具类
 * @author qsyyke
 */


public class SecurityUtil {

    public static String getRequestUri(ServerWebExchange exchange) {

        return exchange.getRequest().getURI().getPath();
    }
}
