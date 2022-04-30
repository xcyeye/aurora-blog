package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author qsyyke
 */


@EnableOpenApi
@EnableDiscoveryClient
@SpringBootApplication
public class AuroraFileRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraFileRun.class,args);
    }
}
