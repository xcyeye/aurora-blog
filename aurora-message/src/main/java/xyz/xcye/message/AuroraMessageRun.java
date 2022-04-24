package xyz.xcye.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;
import xyz.xcye.message.feign.UserFeignService;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

/**
 * @author qsyyke
 */


@EnableOpenApi
@EnableFeignClients(basePackageClasses = {UserFeignService.class})
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication
public class AuroraMessageRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraMessageRun.class, args);
    }
}
