package xyz.xcye.feign.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import xyz.xcye.feign.config.util.RequestUtils;

import java.util.Map;
import java.util.Optional;

@Component
public class GlobalOauth2RequestInterceptor implements RequestInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RequestUtils requestUtils;

    @Override
    public void apply(RequestTemplate template) {
        Map<String, String> requestHeads = requestUtils.getRequestHeadsFromHolder();
        Optional.ofNullable(requestHeads).ifPresent(t -> requestHeads.forEach(template::header));
    }
}