package xyz.xcye.admin.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 默认值
 * @author qsyyke
 */

@Configuration
@ConfigurationProperties(prefix = "aurora.admin.default-value")
@Data
public class DefaultValueEntity {
    private String role;
    private String permission;
    private String avatar;
    private String nickname;
    private String gender;
}
