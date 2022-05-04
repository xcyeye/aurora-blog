package xyz.xcye.comment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置swagger
 */

@Configuration
public class CommentWebConfig implements WebMvcConfigurer {


    /**
     * 自定义全局异常处理
     * @return
     */
    /*@Bean
    public AuroraGlobalExceptionHandler customGlobalExceptionHandler() {
        return new AuroraGlobalExceptionHandler();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }*/
}
