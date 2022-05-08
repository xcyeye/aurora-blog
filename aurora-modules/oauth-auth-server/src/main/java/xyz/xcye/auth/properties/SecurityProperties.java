package xyz.xcye.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 14:27
 */

@Data
@ConfigurationProperties(prefix = SecurityProperties.SECURITY_PROPERTIES)
public class SecurityProperties {

    public static final String SECURITY_PROPERTIES = "aurora.oauth";

    /**
     * access_token过期时间，单位秒
     */
    private Integer accessTokenValiditySeconds = 259200;
    /**
     * refresh_token的过期时间
     */
     private Integer refreshTokenValiditySeconds = 259200;


}
