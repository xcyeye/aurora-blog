package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

/**
 * @author qsyyke
 */

@SpringBootApplication
@EnableFeignClients
public class AuroraCommentRun {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuroraCommentRun.class, args);
        System.out.println(run.getBean(DataSource.class).getClass().getSimpleName());
    }
}
