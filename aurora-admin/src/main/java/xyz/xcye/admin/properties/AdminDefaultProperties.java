package xyz.xcye.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 一些默认值，没有存放在数据库
 * @author qsyyke
 * @date Created in 2022/4/30 16:26
 */

@Data
@ConfigurationProperties(prefix = AdminDefaultProperties.ADMIN_DEFAULT_PREFIX)
public class AdminDefaultProperties {
    public static final String ADMIN_DEFAULT_PREFIX = "aurora.default";

    private String role;

    private String permission;

    private String avatar;

    private String nickname;

    private String gender;

}
