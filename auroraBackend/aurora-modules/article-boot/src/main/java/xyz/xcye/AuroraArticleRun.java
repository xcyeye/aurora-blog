package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 14:38
 */


@EnableFeignClients
@SpringBootApplication
public class AuroraArticleRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraArticleRun.class, args);
    }
}
