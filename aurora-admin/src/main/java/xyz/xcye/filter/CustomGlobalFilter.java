package xyz.xcye.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.xcye.common.entity.R;
import xyz.xcye.common.enums.ResultStatusCode;
import xyz.xcye.myenums.TokenEnum;
import xyz.xcye.common.util.ObjectConvertJson;
import xyz.xcye.common.util.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 这是一个全局拦截器
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomGlobalFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.error("拦截器执行");
        String authenticationToken = request.getHeader("authentication_token");
        if (authenticationToken == null) {
            return false;
        }

        //判断此token是否失效
        boolean expiration = false;
        try {
            expiration = JwtUtil.isExpiration(authenticationToken,
                    TokenEnum.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error("解析token失效");
        }

        if (!expiration) {
            //token过期
            R failureResult = R.failure(ResultStatusCode.PERMISSION_TOKEN_EXPIRATION.getCode(),
                    ResultStatusCode.PERMISSION_TOKEN_EXPIRATION.getMessage());

            String jsonToString = ObjectConvertJson.jsonToString(failureResult);

            log.error(jsonToString);

            //设置响应头
            response.setContentType("application/json;charset=UTF-8;");
            PrintWriter writer = response.getWriter();
            writer.write(jsonToString);
            writer.flush();
            return false;
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
}
