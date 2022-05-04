package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qsyyke
 */

@SpringBootApplication
@EnableFeignClients
public class AuroraAdminMain {
    public static void main(String[] args) {
        SpringApplication.run(AuroraAdminMain.class, args);
    }
}
