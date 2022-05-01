package xyz.xcye.aurora.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 12:00
 */

@Configuration
@EnableConfigurationProperties({AuroraProperties.class,
        AuroraProperties.AuroraFileProperties.class,
        AuroraProperties.AuroraPaginationProperties.class,
        AuroraProperties.AuroraAccountProperties.class,
        AuroraProperties.AuroraAmqpProperties.class})
public class AuroraAutoConfiguration {

}
