package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author qsyyke
 */

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class AuroraMessageRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraMessageRun.class, args);
    }
}
