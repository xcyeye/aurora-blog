package xyz.xcye.aurora.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.constant.OpenApiConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * @author qsyyke
 */

@Component
public class AuroraGlobalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 下面这个是暂时解决feign调用，没有RequestContextHolder的问题
        String requestFeignHeader = request.getHeader(RequestConstant.REQUEST_OPEN_FEIGN_HEADER);

        configSpringDoc(request);

        if ("true".equals(requestFeignHeader)) {
            return true;
        }

        // 判断是否是白名单
        boolean whiteUrl = checkWhiteUrl(request);

        // 判断是否是从认证中心发送的请求，认证中心需要查询密码，不需要拦截
        boolean sendFromAuthServer = sendFromAuthServer(request);

        // 如果是白名单或者是从认证中心发出的查询密码的请求，直接放行，如果请求头中存在token的话，那么会将用户认证信息添加到RequestContextHoler
        if (whiteUrl || sendFromAuthServer) {
            JwtUserInfo jwtUserInfo = null;
            try {
                jwtUserInfo = AuroraRequestUtils.getJwtUserInfo(request);
            } catch (Exception ignored) {}
            Optional.ofNullable(jwtUserInfo).ifPresent(this::storageAuthenticatedUserToAttributes);
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

        // 存储用户认证信息到RequestContextHolder
        storageAuthenticatedUserToAttributes(jwtUserInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // response.setHeader("Access-Control-Max-Age", "3600");
        // response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 检查当前restFul风格的请求地址，是否是白名单
     * @param request 请求对象
     * @return true是白名单
     */
    private boolean checkWhiteUrl(HttpServletRequest request) {

        // 获取请求头中的白名单标识
        String whiteUrlStatus = Optional.ofNullable(request.getHeader(RequestConstant.REQUEST_WHITE_URL_STATUS)).orElse("false");

        // 把当前请求地址的白名单判定状态放入RequestContextHolder中，方便取用
        RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        currentRequestAttributes.setAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, whiteUrlStatus, 1);
        return "true".equals(whiteUrlStatus);
    }

    /**
     * 判断该请求是否是从认证中心发出的，在认证中心，验证用户信息，需要发送请求查询密码，发送的是否，会向
     * 请求头中，添加一个标识，并不是所有从认证中心发出的请求都有这个请求头
     * @param request 请求
     * @return true是从认证中心发出的
     */
    private boolean sendFromAuthServer(HttpServletRequest request) {
        // 判断当前请求是否是从认证中心发出的
        String oauthQueryPwdHeader = request.getHeader(RequestConstant.REQUEST_OAUTH_SERVER_QUERY_PASSWORD);
        return "true".equals(oauthQueryPwdHeader);
    }

    /**
     * 将用户已认证的信息存储到RequestContextHolder
     * @param jwtUserInfo 已认证用户信息，包括jwt等
     */
    private void storageAuthenticatedUserToAttributes(JwtUserInfo jwtUserInfo) {
        RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        Objects.requireNonNull(currentRequestAttributes);
        // 运行到这里，说明token没有失效，将用户已认证的数据，放入RequestContextHolder中，方便使用
        currentRequestAttributes.setAttribute(RequestConstant.REQUEST_STORAGE_JWT_USER_INFO_NAME, jwtUserInfo, 1);
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
