package xyz.xcye.admin.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import xyz.xcye.admin.interceptor.AdminGlobalHandlerInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import xyz.xcye.common.exception.CustomGlobalExceptionHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 配置swagger
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminGlobalHandlerInterceptor globalHandlerInterceptor;

    @Bean(name = "commentBeanPostProcessor")
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            @SuppressWarnings({"NullableProblems", "unchecked"})
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    try {
                        Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                        if (null != field) {
                            field.setAccessible(true);
                            List<RequestMappingInfoHandlerMapping> mappings = (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                            mappings.removeIf(e -> null != e.getPatternParser());
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
                return bean;
            }
        };
    }

    /**
     * 增加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(globalHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/css/**","/js/**","/images/**","/fonts/**");

    }

    /**
     * 自定义全局异常处理
     * @return
     */
    /*@Bean
    public CustomGlobalExceptionHandler customGlobalExceptionHandler() {
        return new CustomGlobalExceptionHandler();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
