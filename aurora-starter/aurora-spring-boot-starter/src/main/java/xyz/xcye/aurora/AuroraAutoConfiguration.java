package xyz.xcye.aurora;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.xcye.aurora.properties.AuroraProperties;

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
