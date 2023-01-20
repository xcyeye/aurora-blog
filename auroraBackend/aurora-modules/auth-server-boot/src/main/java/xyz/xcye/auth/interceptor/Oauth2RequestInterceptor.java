package xyz.xcye.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import xyz.xcye.auth.constant.RequestConstant;

@Component
public class Oauth2RequestInterceptor implements RequestInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void apply(RequestTemplate template) {
        // 判断当前请求的uri是否是查询登录密码的，如果是，则增加一个标识
        String restFulPath = template.method() + ":" + template.url();

        if (antPathMatcher.match("POST:/admin/user/pwd/*", restFulPath) ||
                antPathMatcher.match("GET:/admin/permissionRelation/userRole/*", restFulPath)) {
            // 在请求头中设置一个当前的请求是一个从认证中心发出的查询密码请求
            template.header(RequestConstant.REQUEST_OAUTH_SERVER_QUERY_PASSWORD, "true");
        }
    }
}