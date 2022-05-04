package xyz.xcye.feign.config;

import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:36
 */

@Configuration
public class FeignAutoConfig {

    @ConditionalOnMissingBean
    @Profile(value = {"dev","test"})
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }

    @ConditionalOnMissingBean
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
