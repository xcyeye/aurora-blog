package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author qsyyke
 */


@EnableOpenApi
//@EnableWebMvc
@EnableDiscoveryClient
@SpringBootApplication
public class AuroraFileRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraFileRun.class,args);
    }
}
