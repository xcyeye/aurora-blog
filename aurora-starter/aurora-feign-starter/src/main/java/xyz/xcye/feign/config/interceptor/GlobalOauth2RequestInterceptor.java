package xyz.xcye.feign.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.feign.config.request.AuroraRequestAttributes;
import xyz.xcye.feign.config.util.RequestUtils;

import java.util.Map;

@Component
public class GlobalOauth2RequestInterceptor implements RequestInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RequestUtils requestUtils;

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
        if (currentRequestAttributes instanceof AuroraRequestAttributes) {
            // 设置白名单的状态
            AuroraRequestAttributes auroraRequestAttributes = (AuroraRequestAttributes) currentRequestAttributes;
            String whiteUrlStatus = (String) auroraRequestAttributes.getAttribute(RequestConstant.CONTEXT_WHITE_URL_STATUS, 1);
            template.header(RequestConstant.REQUEST_WHITE_URL_STATUS, whiteUrlStatus);
        }
        Map<String, String> requestHeads = requestUtils.getRequestHeadsFromHolder();
        requestHeads.forEach((key,value) -> {
            if (!"host".equals(key)) {
                template.header(key, value);
            }
        });
    }
}