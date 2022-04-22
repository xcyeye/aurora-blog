package xyz.xcye.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

/**
 * @author qsyyke
 */


@EnableScheduling
@EnableFeignClients(basePackages = {"xyz.xcye.web.common.service.feign"})
@SpringBootApplication
public class AuroraCommentRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraCommentRun.class, args);
    }
}
