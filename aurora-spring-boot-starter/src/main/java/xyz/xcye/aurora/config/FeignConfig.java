package xyz.xcye.aurora.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qsyyke
 * @date Created in 2022/5/1 13:33
 */

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level logger() {
        return Logger.Level.FULL;
    }
}
