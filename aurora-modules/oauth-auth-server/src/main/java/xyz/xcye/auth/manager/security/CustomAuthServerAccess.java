package xyz.xcye.auth.manager.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import xyz.xcye.auth.constant.RequestConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 这是一个自定义的判断逻辑，用在security的access()方法中
 * @author qsyyke
 * @date Created in 2022/5/4 09:51
 */


public class CustomAuthServerAccess {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        // 组装成restFul风格的请求

        String whiteUrlStatus = request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS);
        // 如果当前的路径是白名单，则直接放行
        if ("true".equals(whiteUrlStatus)) {
            return true;
        }

        // 判断

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals(requestURI)) {
                return true;
            }
        }
        return false;
    }
}