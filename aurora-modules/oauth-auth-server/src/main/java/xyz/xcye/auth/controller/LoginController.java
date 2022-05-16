package xyz.xcye.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.core.dto.JwtEntityDTO;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.jwt.JwtUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * 退出登录控制器
 * @author qsyyke
 * @date Created in 2022/5/15 23:50
 */

@RequestMapping("/auth/logout")
@RestController
public class LoginController {

    @Autowired
    private AuroraProperties.AuroraAuthProperties auroraAuthProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 将当前用户的token放入redis中，也就是黑名单，持有这个jwt的用户，都不能登录
        storageJwtBlacklist(request);
        //cancelCookie(request, response);
    }

    private void storageJwtBlacklist(HttpServletRequest request) {
        JwtUserInfo jwtUserInfo = null;
        try {
            jwtUserInfo = AuroraRequestUtils.getJwtUserInfo(request);
        } catch (Exception e) {
            // 可能用户没有登录，直接退出
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }

        // 获取jwt
        String jwtToken = jwtUserInfo.getJwtToken();

        // 获取失效时间
        JwtEntityDTO jwtEntityDTO = JwtUtils.parseJwtToken(jwtToken, auroraAuthProperties.getSecretKey());
        System.out.println(jwtEntityDTO);

        // jwt如redis黑名单中 失效时间就是此token的失效时间
        Duration duration = Duration.ofSeconds(jwtEntityDTO.getExpirationAt().getTime());
        //redisTemplate.opsForValue().set(AuthRedisConstant.STORAGE_JWT_BLACKLIST_PREFIX + jwtEntityDTO.getJti(), jwtToken, duration);
    }

    /**
     * 取消cookie的设置
     * @param request
     * @param response
     */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(OauthJwtConstant.COOKIE_STORAGE_LOGIN_SUCCESS_STATUS, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        if (auroraAuthProperties.getCookieDomain() != null) {
            cookie.setDomain(auroraAuthProperties.getCookieDomain());
        }
        cookie.setSecure((auroraAuthProperties.getUseSecureCookie() != null) ?
                auroraAuthProperties.getUseSecureCookie() : request.isSecure());
        response.addCookie(cookie);
    }

    /**
     * 获取path
     * @param request
     * @return
     */
    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return (contextPath.length() > 0) ? contextPath : "/";
    }
}
