package xyz.xcye.comment.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author qsyyke
 */

@Configuration
public class CommentOpenFeignConfig {

    /**
     * 设置openFeign调用时的日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
