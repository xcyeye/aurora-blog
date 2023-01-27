package xyz.xcye.aurora.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 11:59
 */

@Data
@ConfigurationProperties(prefix = AuroraProperties.AURORA_PREFIX)
public class AuroraProperties {
    /**
     * 主题配置文件的前缀
     */
    public static final String AURORA_PREFIX = "aurora";

    /**
     * 使用雪花算法生成id时的本机工作id
     */
    private Integer snowFlakeWorkerId;

    /**
     * 使用雪花算法生成id时的数据中心机房id
     */
    private Integer snowFlakeDatacenterId;

    private List<String> defaultRoleList;

    @Data
    @ConfigurationProperties(prefix = AuroraDefaultUserInfoProperties.AURORA_DEFAULT_USER_INFO_PREFIX)
    public static class AuroraDefaultUserInfoProperties {
        public static final String AURORA_DEFAULT_USER_INFO_PREFIX = "aurora.default.user";

        private String siteInfo;

        private String navbarInfo;

        private String pageInfo;

        private String welcomeArticle;
    }

    @Data
    @ConfigurationProperties(prefix = AuroraFileProperties.AURORA_FILE_PREFIX)
    public static class AuroraFileProperties {
        public static final String AURORA_FILE_PREFIX = "aurora.file";

        /**
         * 在nginx中配置的location/root值，用于将文件上传到此root目录中，会自动为该文件生成url地址
         */
        private String nginxRootPath;

        /**
         * nginx中配置的server-name值
         */
        private String nginxServerName;

        /**
         * 将上传文件保存在哪个目录下，会自动在nginx的location/root对应的目录下，创建该文件夹
         */
        private String saveFileFolder;
    }

    @ConfigurationProperties(prefix = AuroraPaginationProperties.AURORA_PAGINATION_PREFIX)
    @Data
    public static class AuroraPaginationProperties {
        public static final String AURORA_PAGINATION_PREFIX = "aurora.pagination";

        /**
         * 分页的页数
         */
        private Integer pageNum;

        /**
         * 分页的大小
         */
        private Integer pageSize;
    }

    @Data
    @ConfigurationProperties(prefix = AuroraAccountProperties.AURORA_ACCOUNT_PREFIX)
    public static class AuroraAccountProperties {
        public static final String AURORA_ACCOUNT_PREFIX = "aurora.account";

        /**
         * 使用发送邮件的方式验证账户，验证链接的失效时间
         */
        private Long mailVerifyAccountExpirationTime;

        /**
         * 使用发送邮件的方式验证账户，验证链接的前缀，会进行拼接，比如此值为http://localhost，最终用户点击的链接会变成http://localhost/user/23748623
         */
        private String mailVerifyAccountPrefixPath;

        private String enableAccountPrefixPath;
    }

    @ConfigurationProperties(prefix = AuroraAuthProperties.AURORA_AUTH_PREFIX)
    @Data
    public static class AuroraAuthProperties {
        public static final String AURORA_AUTH_PREFIX = "aurora.oauth";

        /**
         * 秘钥
         */
        private String secretKey;

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
         * cookie失效时间
         */
        private Integer tokenValiditySeconds;

        /**
         * cookie的域名
         */
        private String cookieDomain;

        /**
         * cookie的失效时间，单位为秒
         */
        private Integer cookieMaxAge;

        /**
         * 请看https://www.cnblogs.com/forwill/p/6181984.html
         */
        private Boolean useSecureCookie;

        /**
         * 验证链接中的秘钥
         */
        private String verifySecretKey;
    }
}
