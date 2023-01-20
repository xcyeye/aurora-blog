package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:49
 */

@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class AuthServerRun {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerRun.class, args);
    }
}
