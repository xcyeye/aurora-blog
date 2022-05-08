package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qsyyke
 */

@EnableFeignClients
@SpringBootApplication
public class AuroraGateWayMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraGateWayMain.class, args);
    }
}
