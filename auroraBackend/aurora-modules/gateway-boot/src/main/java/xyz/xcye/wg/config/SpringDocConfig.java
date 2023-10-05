package xyz.xcye.wg.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringDocConfig {

    @Autowired
    private OpenAPI openAPI;

    /**
     * 使用springdoc聚合文档
     *
     * @param swaggerUiConfigParameters
     * @param locator
     * @return
     */
    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (definitions == null) {
            return groups;
        }

        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches("aurora.*"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("aurora-", "");
                    swaggerUiConfigParameters.addGroup(routeDefinition.getId());
                    GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(routeDefinition.getId()).build();
                });
        return groups;
    }
}

