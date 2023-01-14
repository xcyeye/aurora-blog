package xyz.xcye.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 13:43
 */

@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
public class AuroraOpenApiAutoConfig {

    @Autowired
    private SwaggerProperties properties;

    @Primary
    @Bean
    public OpenAPI customOpenApi() {
        // 初始化properties属性
        properties = Optional.ofNullable(properties).orElse(new SwaggerProperties());
        properties.setApiInfo(Optional.ofNullable(properties.getApiInfo()).orElse(new SwaggerProperties.ApiInfo()));
        properties.setAuthor(Optional.ofNullable(properties.getAuthor()).orElse(new SwaggerProperties.Author()));
        License license = new License()
                .name(properties.getApiInfo().getLicense())
                .url(properties.getApiInfo().getLicenseUrl());

        Contact contact = new Contact()
                .email(properties.getAuthor().getEmail())
                .name(properties.getAuthor().getName())
                .url(properties.getAuthor().getUrl());

        Info info = new Info().title(properties.getApiInfo().getTitle())
                .description(properties.getApiInfo().getDescription())
                .version(properties.getApiInfo().getVersion())
                .license(license).contact(contact);
        return new OpenAPI().info(info).components(new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    /**
     * 网关的地址，用于聚合springdoc文档的时候用到
     */
    @Value("${aurora.gateway.server-base-url:http://localhost:7777}")
    private String gatewayServerBaseUrl;

    @Bean
    public ServerBaseUrlCustomizer serverBaseUrlCustomizer() {
        return t -> gatewayServerBaseUrl;
    }
}
