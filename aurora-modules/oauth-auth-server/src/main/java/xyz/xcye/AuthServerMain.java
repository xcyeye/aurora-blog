package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import xyz.xcye.auth.handler.OauthServerAuthenticationFailureHandler;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:49
 */

@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class AuthServerMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuthServerMain.class, args);
        System.out.println(run.getBean(OauthServerAuthenticationFailureHandler.class));
        System.out.println(34);
    }
}
