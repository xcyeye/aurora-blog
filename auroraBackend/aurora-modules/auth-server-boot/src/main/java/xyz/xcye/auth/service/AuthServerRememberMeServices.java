package xyz.xcye.auth.service;

import cn.hutool.core.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.auth.constant.OauthJwtConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/15 20:14
 */

@Component
public class AuthServerRememberMeServices implements RememberMeServices {

    @Autowired
    private AuroraProperties.AuroraAuthProperties auroraAuthProperties;

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        // 登录成功，存储到cookie
        // 默认三十分钟
        int maxAge = Optional.ofNullable(auroraAuthProperties.getCookieMaxAge()).orElse(60 * 30);
        setCookie(maxAge, request, response, successfulAuthentication);
    }

    /**
     * 设置cookie
     *
     * @param maxAge
     * @param request
     * @param response
     * @param successfulAuthentication
     */
    protected void setCookie(int maxAge, HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String cookieValue = encodeCookie(calculateExpiryTime(maxAge), retrieveUserName(successfulAuthentication));
        Cookie cookie = new Cookie(OauthJwtConstant.COOKIE_STORAGE_LOGIN_SUCCESS_STATUS, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));
        if (StringUtils.hasLength(auroraAuthProperties.getCookieDomain())) {
            cookie.setDomain(auroraAuthProperties.getCookieDomain());
        }
        if (maxAge < 1) {
            cookie.setVersion(1);
        }
        cookie.setSecure((auroraAuthProperties.getUseSecureCookie() != null) ?
                auroraAuthProperties.getUseSecureCookie() : request.isSecure());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 计算cookie失效时间
     *
     * @param maxAge
     * @return
     */
    private long calculateExpiryTime(int maxAge) {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis + (maxAge * 1000L);
    }

    /**
     * 获取path
     *
     * @param request
     * @return
     */
    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return (contextPath.length() > 0) ? contextPath : "/";
    }

    /**
     * 对失效时间和用户名进行一个编码
     *
     * @param cookieExpiryTime
     * @param username
     * @return
     */
    protected String encodeCookie(long cookieExpiryTime, String username) {
        String data = username + ":" + cookieExpiryTime + ":" + OauthJwtConstant.STORAGE_COOKIE_SECRET_KEY;
        return Base64.encode(data);
    }

    /**
     * 获取用户名
     *
     * @param authentication
     * @return
     */
    protected String retrieveUserName(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication.getPrincipal().toString();
    }

    /**
     * 获取密码
     *
     * @param authentication
     * @return
     */
    protected String retrievePassword(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getPassword();
        }
        if (authentication.getCredentials() != null) {
            return authentication.getCredentials().toString();
        }
        return null;
    }

    /**
     * 判断实例
     *
     * @param authentication
     * @return
     */
    private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }
}
