package xyz.xcye.comment.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import xyz.xcye.web.common.exception.CustomGlobalExceptionHandler;
import xyz.xcye.web.common.manager.advice.ResponseResultHandler;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 配置swagger
 */
@Configuration
public class CommentWebConfig {
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
     * 自定义全局异常处理
     * @return
     */
    @Bean
    public CustomGlobalExceptionHandler customGlobalExceptionHandler() {
        return new CustomGlobalExceptionHandler();
    }

    /**
     * 响应结果封装
     * @return
     */
    @Bean
    public ResponseResultHandler responseResultHandler() {
        return new ResponseResultHandler();
    }
}
