package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author qsyyke
 */

@EnableScheduling
@SpringBootApplication
public class AuroraMessageMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraMessageMain.class, args);
    }
}
