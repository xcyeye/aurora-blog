package xyz.xcye.aurora.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author qsyyke
 */


@Component
public class AuroraGlobalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的白名单标识
        String whiteUrlStatus = Optional.ofNullable(request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS)).orElse("false");

        // 把当前请求地址的白名单判定状态放入RequestContextHolder中，方便取用
        RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        currentRequestAttributes.setAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, whiteUrlStatus, 1);
        if ("true".equals(whiteUrlStatus)) {
            return true;
        }

        // 判断当前请求是否是从认证中心发出的
        String oauthQueryPwdHeader = request.getHeader(RequestConstant.REQUEST_OAUTH_SERVER_QUERY_PASSWORD);
        if ("true".equals(oauthQueryPwdHeader)) {
            return true;
        }

        // 从请求头中获取jwtUserInfo对象
        JwtUserInfo jwtUserInfo = AuroraRequestUtils.getJwtUserInfo(request);
        // 获取请求头的token，在网关的全局过滤器中，被加入
        String jwtToken = jwtUserInfo.getJwtToken();

        // 判断jwt是否失效
        boolean tokenExpiration = AuroraRequestUtils.tokenExpiration(jwtToken);
        if (tokenExpiration) {
            // token过期或者无效
            return AuroraRequestUtils.returnFailureAndResponseJsonText(response, ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        // 运行到这里，说明token没有失效，将用户已认证的数据，放入RequestContextHolder中，方便使用
        currentRequestAttributes.setAttribute(RequestConstant.REQUEST_STORAGE_JWT_USER_INFO_NAME, jwtUserInfo, 1);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
