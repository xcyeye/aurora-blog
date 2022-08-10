package xyz.xcye.amqp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:50
 */

@EnableConfigurationProperties({AuroraAmqpProperties.class})
@Configuration
public class RabbitMQAutoConfig {
}
