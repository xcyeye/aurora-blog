package xyz.xcye.article.test;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringdocOpenapiConfiguration implements WebMvcConfigurer {

    private final SwaggerProperties swaggerProperties;

    public SpringdocOpenapiConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public OpenAPI springDocOpenAPI() {
        //配置认证、请求头参数
        Components components = new Components();
        Map<String, Object> myHeader2extensions = new HashMap<>(2);
        myHeader2extensions.put("name", "myHeader2");
        components
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1"))
                //注：这种方式有问题，不推荐
                .addHeaders("myHeader2", new Header().description("myHeader2 header").schema(new StringSchema()).extensions(myHeader2extensions))
                .addParameters("myGlobalHeader", new HeaderParameter().required(true).name("My-Global-Header").description("My Global Header").schema(new StringSchema()).required(false))
        ;

        // 接口调试路径
        Server tryServer = new Server();
        tryServer.setUrl(swaggerProperties.getTryHost());

        return new OpenAPI()
                .components(components)
                .servers(Collections.singletonList(tryServer))
                .info(new Info()
                        .title(swaggerProperties.getApplicationName() + " Api Doc")
                        .description(swaggerProperties.getApplicationDescription())
                        .version("Application Version: " + swaggerProperties.getApplicationVersion() + "\n Spring Boot Version: " + SpringBootVersion.getVersion())
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Full Documentation")
                        .url("https://springdoc.org/")
                );
    }

    /**
     * 添加全局的请求头参数
     */
    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/myGlobalHeader"));
                });
    }

    /**
     * 通用拦截器排除设置，所有拦截器都会自动加springdoc-opapi相关的资源排除信息，不用在应用程序自身拦截器定义的地方去添加，算是良心解耦实现。
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration.excludePathPatterns("/springdoc**/**");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

