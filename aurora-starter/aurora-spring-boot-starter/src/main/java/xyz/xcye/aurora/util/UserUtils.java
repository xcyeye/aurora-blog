package xyz.xcye.aurora.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.xcye.core.constant.oauth.OauthJwtConstant;
import xyz.xcye.core.dto.JwtUserInfo;

/**
 * 这个类是通用的和用户相关的工具类
 * @author qsyyke
 * @date Created in 2022/5/11 19:46
 */

@Component
public class UserUtils {

    /**
     * 从RequestContextHolder中获取当前已验证的用户
     * @return
     */
    public JwtUserInfo getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        JwtUserInfo jwtUserInfo = null;
        if (requestAttributes != null) {
            jwtUserInfo = (JwtUserInfo) requestAttributes.getAttribute(OauthJwtConstant.REQUEST_STORAGE_JWT_USER_INFO_NAME, 1);
        }
        return jwtUserInfo;
    }
}
