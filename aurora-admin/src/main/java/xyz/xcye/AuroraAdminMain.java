package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author qsyyke
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableOpenApi
@EnableWebMvc
@EnableFeignClients
public class AuroraAdminMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraAdminMain.class,args);
    }
}
