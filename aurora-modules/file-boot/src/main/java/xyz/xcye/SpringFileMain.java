package xyz.xcye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 15:35
 */

@SpringBootApplication
public class SpringFileMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringFileMain.class, args);
        System.out.println(run.getBean(RedisTemplate.class));
    }
}
