package xyz.xcye.aurora.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.lambda.AssertUtils;

/**
 * 这个类是通用的和用户相关的工具类
 * @author qsyyke
 * @date Created in 2022/5/11 19:46
 */

@Component
public class UserUtils {

    /**
     * 从RequestContextHolder中获取当前已验证的用户 因为发送
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

    public Long getCurrentUserUid() {
        JwtUserInfo currentUser = getCurrentUser();
        AssertUtils.stateThrow(currentUser != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        return currentUser.getUserUid();
    }
}
