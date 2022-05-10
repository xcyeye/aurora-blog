package xyz.xcye.aurora.interceptor;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.xcye.auth.enums.TokenConstant;
import xyz.xcye.core.constant.oauth.OauthJwtConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.jwt.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author qsyyke
 */


@Component
@Slf4j
public class AuroraGlobalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        // 获取请求头中的白名单标识
        String whiteUrlFlag = Optional.ofNullable(request.getHeader(OauthJwtConstant.REQUEST_WHITE_URL_FLAG_NAME)).orElse("false");
        if ("true".equals(whiteUrlFlag)) {
            return true;
        }

        String jwtUserInfoBase64 = request.getHeader(OauthJwtConstant.REQUEST_TOKEN_NAME);
        String jwtUserInfoStr = Base64.decodeStr(jwtUserInfoBase64);

        // 将jwtUserInfoStr解析成一个jwtUserInfo对象
        JwtUserInfo jwtUserInfo = JSON.parseObject(jwtUserInfoStr, JwtUserInfo.class);

        // 获取请求头的token，在网关的全局过滤器中，被加入
        String jwtToken = request.getHeader(OauthJwtConstant.REQUEST_JWT_TOKEN_NAME);

        // 判断请求头中的jwtToken和jwtUserInfo是否存在，如果不存在，返回false
        if (jwtToken == null || jwtUserInfo == null) {
            return setResponseJsonText(response, ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }

        /*
        * 从请求头中xid，如果此xid不为null，则绑定到RootContext中
        * */
        /*String globalXid = request.getHeader(SeataConstant.GLOBAL_XID_REQUEST_HEADER_NAME);
        if (StringUtils.hasLength(globalXid)) {
            RootContext.bind(globalXid);
        }*/

        // 判断此token是否失效
        boolean expiration = false;
        try {
            expiration = JwtUtils.isExpiration(jwtToken, TokenConstant.SIGN_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (RuntimeException e) {
            return setResponseJsonText(response, ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        if (!expiration) {
            //token过期
            return setResponseJsonText(response,ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }
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

    private boolean setResponseJsonText(HttpServletResponse response, ResponseStatusCodeEnum statusCodeEnum) throws IOException {
        //token过期
        R failureResult = R.failure(statusCodeEnum.getCode(), statusCodeEnum.getMessage());
        String jsonToString = ConvertObjectUtils.jsonToString(failureResult);
        //设置响应头
        response.setContentType("application/json;charset=UTF-8;");
        PrintWriter writer = response.getWriter();
        writer.write(jsonToString);
        writer.flush();
        return false;
    }
}
