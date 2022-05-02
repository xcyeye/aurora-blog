package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import xyz.xcye.aurora.feign.MessageLogFeignService;

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
        SpringApplication.run(AuroraAdminMain.class, args);
    }
}
