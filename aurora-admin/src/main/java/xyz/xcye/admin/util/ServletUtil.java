package xyz.xcye.admin.util;

import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author qsyyke
 */


public class ServletUtil {
    public static HttpServletResponse setContentType(HttpServletResponse response) {
        response.setContentType("application/json;UTF-8;");
        return response;
    }
}
