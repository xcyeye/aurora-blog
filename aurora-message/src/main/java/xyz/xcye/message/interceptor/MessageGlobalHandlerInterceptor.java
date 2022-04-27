package xyz.xcye.message.interceptor;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.xcye.common.constant.SeataConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qsyyke
 */


@Component
@Slf4j
public class MessageGlobalHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        * 从请求头中xid，如果此xid不为null，则绑定到RootContext中
        * */
        String globalXid = request.getHeader(SeataConstant.GLOBAL_XID_REQUEST_HEADER_NAME);
        if (StringUtils.hasLength(globalXid)) {
            RootContext.bind(globalXid);
        }

        /*String authenticationToken = request.getHeader("authentication_token");
        if (authenticationToken == null) {
            return false;
        }

        //判断此token是否失效
        boolean expiration = false;
        try {
            expiration = JwtUtils.isExpiration(authenticationToken,
                    TokenEnum.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error("解析token失效");
        }

        if (!expiration) {
            //token过期
            R failureResult = R.failure(ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getCode(),
                    ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getMessage());

            String jsonToString = ConvertObjectUtils.jsonToString(failureResult);

            log.error(jsonToString);

            //设置响应头
            response.setContentType("application/json;charset=UTF-8;");
            PrintWriter writer = response.getWriter();
            writer.write(jsonToString);
            writer.flush();
            return false;
        }*/

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
