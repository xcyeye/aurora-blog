package xyz.xcye.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:36
 */

@Configuration
public class FeignAutoConfig {
    @Profile(value = {"dev","test"})
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }


}
