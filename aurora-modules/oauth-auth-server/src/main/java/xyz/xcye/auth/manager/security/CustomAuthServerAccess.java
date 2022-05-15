package xyz.xcye.auth.manager.security;

import org.springframework.stereotype.Component;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.token.TokenException;

import javax.servlet.http.HttpServletRequest;

/**
 * 这是一个自定义的判断逻辑，用在security的access()方法中
 * @author qsyyke
 * @date Created in 2022/5/4 09:51
 */

@Component
public class CustomAuthServerAccess {

    public boolean hasPermission(HttpServletRequest request) {
        String whiteUrlStatus = request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS);
        // 如果当前的路径是白名单，则直接放行
        if ("true".equals(whiteUrlStatus)) {
            return true;
        }

        // 从请求头中获取jwtUserInfo对象
        JwtUserInfo jwtUserInfo = AuroraRequestUtils.getJwtUserInfo(request);
        // 获取请求头的token，在网关的全局过滤器中，被加入
        String jwtToken = jwtUserInfo.getJwtToken();

        // 判断jwt是否失效
        boolean tokenExpiration = AuroraRequestUtils.tokenExpiration(jwtToken);
        if (tokenExpiration) {
            // token过期或者无效 直接抛出异常，auth有自定义的拦截
            throw new TokenException(ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }
        // 运行到这里，说明token存在，并且没有失效，不需要验证是否有权限访问，这是网关做的事
        return true;
    }
}