package xyz.xcye.aurora.util;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.token.TokenException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.jwt.JwtUtils;
import xyz.xcye.core.util.lambda.AssertUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 09:38
 */

public class AuroraRequestUtils {

    public static Map<String,String> getRequestHeadsFromHolder() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Map<String,String> headMaps = new HashMap<>();

        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headName = requestHeaderNames.nextElement();
            String headerValue = request.getHeader(headName);

            // 将请求头名称和值放入map中
            headMaps.put(headName, headerValue);
        }

        return headMaps;
    }

    public static boolean getWhiteUrlFlag() {
        RequestAttributes currentRequestAttributes = null;
        try {
            currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        } catch (IllegalStateException e) {
            LogUtils.logExceptionInfo(e);
            return false;
        }
        String whiteUrlStatus = null;
        try {
            whiteUrlStatus = (String) currentRequestAttributes.getAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, 1);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
            return false;
        }
        return "true".equals(whiteUrlStatus);
    }

    /**
     * 获取当前的请求对象，如果不存在，则返回null
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前的请求对象，如果不存在，则返回null
     * @return
     */
    public static HttpServletResponse getCurrentResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getResponse();
    }

    public static JwtUserInfo getJwtUserInfo(HttpServletRequest request) {
        String jwtUserInfoBase64 = request.getHeader(RequestConstant.REQUEST_TOKEN_NAME);
        String jwtUserInfoStr = Base64.decodeStr(jwtUserInfoBase64);

        // 将jwtUserInfoStr解析成一个jwtUserInfo对象
        JwtUserInfo jwtUserInfo = JSON.parseObject(jwtUserInfoStr, JwtUserInfo.class);
        AssertUtils.stateThrow(jwtUserInfo != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        jwtUserInfo.setRequestHeadMap(getRequestHeadsFromHolder());
        return jwtUserInfo;
    }

    /**
     * 判断传入的token是否过期，如果token已过期，或者无效，返回true，如果token没有过期，返回false
     * @param jwtToken
     * @return true过期，false没有过期
     * @throws IOException 没有传入token，抛出用户未登录
     */
    public static boolean tokenExpiration(String jwtToken) {
        // 判断请求头中的jwtToken和jwtUserInfo是否存在，如果不存在，返回false
        if (!StringUtils.hasLength(jwtToken)) {
            throw new TokenException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN);
        }
        // 判断此token是否失效
        AuroraProperties.AuroraAuthProperties auroraAuthProperties =
                AuroraSpringUtils.getBean(AuroraProperties.AuroraAuthProperties.class);
        return JwtUtils.isExpiration(jwtToken, auroraAuthProperties.getSecretKey());
    }

    public static boolean returnFailureAndResponseJsonText(HttpServletResponse response,
                                                           ResponseStatusCodeEnum statusCodeEnum) throws IOException {
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
