package xyz.xcye.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author qsyyke
 */


@EnableScheduling
@EnableFeignClients(basePackages = {"xyz.xcye.comment.service"})
@SpringBootApplication
public class AuroraCommentRun {
    public static void main(String[] args) {
        SpringApplication.run(AuroraCommentRun.class,args);
    }
}
