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

     /**
      * 如果删除redis中的信息，删除失败，最大重试次数
      */
     private Integer redisDeleteRetry;

     /**
      * 最大的登录失败次数，如果超过这个次数，那么该用户在指定时间内，不能继续登录
      */
     private Integer maxLoginFailure;

     /**
      * 如果用户登录失败次数达到最大，那么多长时间内，该用户可以继续登录，单位是分钟
      */
     private Integer reLoginMinute;

     /**
      * 腾讯地图api地址，包含key部分，用于定位
      */
     private String txMapApi;

    /**
     * 腾讯地图api地址，包含key部分，用于定位
     */
    private Integer tokenValiditySeconds;
}
