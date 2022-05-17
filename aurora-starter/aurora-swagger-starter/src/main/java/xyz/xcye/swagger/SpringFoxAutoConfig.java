package xyz.xcye.swagger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 13:43
 */

@Configuration
@EnableOpenApi
@EnableConfigurationProperties({SwaggerProperties.class})
public class SpringFoxAutoConfig {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            @SuppressWarnings({"NullableProblems", "unchecked"})
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    try {
                        Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                        if (null != field) {
                            field.setAccessible(true);
                            List<RequestMappingInfoHandlerMapping> mappings = (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                            mappings.removeIf(e -> null != e.getPatternParser());
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
                return bean;
            }
        };
    }

    //配置了Swagger的Docket的bean实例
    //enable是否启动swagger，如果为False则Swagger不能在浏览器访问
    //@Bean
    //public Docket docket() {
    //    Set<String> set = new HashSet<>();
    //    set.add("https");
    //    set.add("http");
    //    return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
    //            .enable(true)//定义是否开启swagger，false为关闭，可以通过变量控制
    //            // 微信关注开发者技术前线：定义是否开启swagger，false为关闭，可以通过变量控制
    //            .apiInfo(apiInfo())//将api的元信息设置为包含在json ResourceListing响应中。
    //            .select()
    //            .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
    //            //paths()过滤什么路径
    //            .paths(PathSelectors.any())
    //            .build()
    //            .protocols(set)// 支持的通讯协议集合
    //            .securitySchemes(securitySchemes())// 授权信息设置，必要的header token等认证信息
    //            .securityContexts(securityContexts());// 授权信息全局应用
    //}
    ////配置Swagger 信息 = ApiInfo
    //private ApiInfo apiInfo(){
    //    //作者信息
    //    Contact contact = new Contact(properties.getAuthor().getName(),properties.getAuthor().getUrl(),properties.getAuthor().getEmail());
    //    return new ApiInfo(properties.getApiInfo().getTitle(), properties.getApiInfo().getDescription(),
    //            properties.getApiInfo().getVersion(), properties.getApiInfo().getTermsOfServiceUrl(),
    //            contact, properties.getApiInfo().getLicense(), properties.getApiInfo().getLicenseUrl(),
    //            new ArrayList<>());
    //}
    ///**
    // * 设置授权信息
    // */
    //private List<SecurityScheme> securitySchemes()
    //{
    //    List<ApiKey> result = new ArrayList<>();
    //    ApiKey apiKey = new ApiKey("Authorization","Authorization" ,"Header" );
    //    result.add(apiKey);
    //    return  Collections.singletonList(apiKey);
    //}
    ///**
    // * 授权信息全局应用
    // */
    //private List<SecurityContext> securityContexts() {
    //    return Collections.singletonList(
    //            SecurityContext.builder()
    //                    .securityReferences(Collections.singletonList(new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("global", "Authorization")})))
    //                    .build()
    //    );
    //}
}
