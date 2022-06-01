package xyz.xcye.amqp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:50
 */

@EnableConfigurationProperties({AuroraAmqpProperties.class})
@Configuration
public class RabbitMQAutoConfig {

}
