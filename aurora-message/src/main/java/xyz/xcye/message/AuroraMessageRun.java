package xyz.xcye.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author qsyyke
 */


@EnableOpenApi
@EnableDiscoveryClient
@SpringBootApplication
public class AuroraMessageRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraMessageRun.class,args);
    }
}
