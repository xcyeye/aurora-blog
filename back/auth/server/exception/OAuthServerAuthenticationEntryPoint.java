package xyz.xcye.auth.server.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义AuthenticationEntryPoint，用于
 * clientId，账号，密码等出错处理类
 * @author qsyyke
 * @date Created in 2022/5/4 13:02
 */

@Component
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        System.out.println(3847);

        response.getWriter().write("this client or secret is fail");
    }
}
