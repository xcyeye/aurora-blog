package xyz.xcye.file.interceptor;

/**
 * @author qsyyke
 */


/*@Component
@Slf4j
public class FileGlobalHandlerInterceptor implements HandlerInterceptor {
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
}*/
