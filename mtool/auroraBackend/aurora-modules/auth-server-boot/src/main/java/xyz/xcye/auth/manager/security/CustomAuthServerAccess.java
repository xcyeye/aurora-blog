package xyz.xcye.auth.manager.security;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.auth.util.OauthServerUtils;
import xyz.xcye.core.constant.OpenApiConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这是一个自定义的判断逻辑，用在security的access()方法中
 * @author qsyyke
 * @date Created in 2022/5/4 09:51
 */

@Component
public class CustomAuthServerAccess {

    public boolean hasPermission(HttpServletRequest request) throws IOException {

        String whiteUrlStatus = request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS);
        configSpringDoc(request);
        // 如果当前的路径是白名单，则直接放行
        if ("true".equals(whiteUrlStatus)) {
            return true;
        }

        // 从请求头中获取jwtUserInfo对象
        JwtUserInfo jwtUserInfo = null;
        try {
            jwtUserInfo = AuroraRequestUtils.getJwtUserInfo(request);
        } catch (Exception e) {
            return authenticationFailure(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        // 获取请求头的token，在网关的全局过滤器中，被加入
        String jwtToken = jwtUserInfo.getJwtToken();

        // 判断jwt是否失效
        boolean tokenExpiration = AuroraRequestUtils.tokenExpiration(jwtToken);
        if (tokenExpiration) {
            // token过期或者无效 直接抛出异常，auth有自定义的拦截
            return authenticationFailure(ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }
        // 运行到这里，说明token存在，并且没有失效，不需要验证是否有权限访问，这是网关做的事
        return true;
    }

    private boolean authenticationFailure(ResponseStatusCodeEnum statusCodeEnum) throws IOException {
        HttpServletResponse response = AuroraRequestUtils.getCurrentResponse();
        OauthServerUtils.setHeader(response);
        R r = R.failure(statusCodeEnum.getCode(), statusCodeEnum.getMessage());
        OauthServerUtils.writeJson(response, r);
        return false;
    }

    /**
     * 解决springdoc访问时，不能返回接口信息，也就是判断，如果是springdoc的访问，则在RequestContextHolder中添加一个信息
     * 在AuroraResponseResultHandler中获取是否是springdoc的访问，如果是，则不对响应信息进行处理
     * @param request
     */
    private void configSpringDoc(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match(OpenApiConstant.SPRING_DOC_REQUEST_PATH, requestURI)) {
            RequestContextHolder.currentRequestAttributes()
                    .setAttribute(OpenApiConstant.CONTEXT_REQUEST_HEADER_OF_SPRING_DOC, true, 0);
        }
        //if (OpenApiConstant.SPRING_DOC_REQUEST_PATH.equals(requestURI)) {
        //    RequestContextHolder.currentRequestAttributes()
        //            .setAttribute(OpenApiConstant.CONTEXT_REQUEST_HEADER_OF_SPRING_DOC, true, 1);
        //}
    }
}