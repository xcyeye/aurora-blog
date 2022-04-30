package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;
import xyz.xcye.web.common.service.mq.SendMQMessageService;

/**
 * @author qsyyke
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableOpenApi
@EnableWebMvc
@EnableFeignClients(basePackageClasses = MessageLogFeignService.class)
public class AuroraAdminMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuroraAdminMain.class, args);
        System.out.println(run.getBean(SendMQMessageService.class));
        System.out.println(234234);
    }
}
