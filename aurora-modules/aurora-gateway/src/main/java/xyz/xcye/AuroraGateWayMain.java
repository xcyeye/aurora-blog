package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qsyyke
 */

@EnableDiscoveryClient
@SpringBootApplication
public class AuroraGateWayMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraGateWayMain.class, args);
    }
}
