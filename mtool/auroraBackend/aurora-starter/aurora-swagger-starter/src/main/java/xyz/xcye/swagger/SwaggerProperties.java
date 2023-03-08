package xyz.xcye.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qsyyke
 * @date Created in 2022/5/16 21:40
 */

@ConfigurationProperties(prefix = SwaggerProperties.SWAGGER_PREFIX)
@Data
public class SwaggerProperties {
    public static final String SWAGGER_PREFIX = "aurora.swagger";

    /**
     * 包
     */
    private String basePackage;

    /**
     * 作者
     */
    private Author author;

    /**
     * api相关介绍
     */
    private ApiInfo apiInfo;

    @Data
    public static class ApiInfo {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
    }

    @Data
    public static class Author {
        private String name;
        private String email;
        private String url;
    }
}
