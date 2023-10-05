package xyz.xcye.message.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.xcye.aurora.interceptor.AuroraGlobalHandlerInterceptor;

@Configuration
public class MessageWebConfig implements WebMvcConfigurer {

    @Autowired
    private AuroraGlobalHandlerInterceptor globalHandlerInterceptor;

    /**
     * 增加自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(globalHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/", "/css/**", "/js/**", "/images/**", "/fonts/**");

    }
}
