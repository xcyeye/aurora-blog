package xyz.xcye.admin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.xcye.admin.properties.AdminDefaultProperties;
import xyz.xcye.aurora.interceptor.AuroraGlobalHandlerInterceptor;

/**
 * 配置swagger
 */

@EnableConfigurationProperties({AdminDefaultProperties.class})
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Autowired
    private AuroraGlobalHandlerInterceptor auroraGlobalHandlerInterceptor;

    /**
     * 增加自定义拦截器
     * @param
     */
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(auroraGlobalHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/css/**","/js/**","/images/**","/fonts/**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
