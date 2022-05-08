package xyz.xcye.wg.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import xyz.xcye.wg.handler.AuroraAccessDeniedHandler;
import xyz.xcye.wg.handler.AuroraAuthenticationEntryPoint;
import xyz.xcye.wg.handler.AuroraAuthenticationFailureHandler;


/**
 * @author qsyyke
 */

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    /**
     * JWT的鉴权管理器
     */
    @Autowired
    private ReactiveAuthorizationManager<AuthorizationContext> accessManager;

    /**
     * token校验管理器
     */
    @Autowired
    private ReactiveAuthenticationManager tokenAuthenticationManager;

    /** 自定义认证失败处理类 **/
    @Autowired
    private AuroraAuthenticationFailureHandler auroraAuthenticationFailureHandler;

    /** 自定义未登录访问需要认证的资源时的处理类 **/
    @Autowired
    private AuroraAuthenticationEntryPoint auroraAuthenticationEntryPoint;

    /** 自定义访问被拒绝时处理类 **/
    @Autowired
    private AuroraAccessDeniedHandler auroraAccessDeniedHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        //认证过滤器，放入认证管理器tokenAuthenticationManager
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());

        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                //白名单直接放行
                .pathMatchers("/admin/user").permitAll()
                //其他的请求必须鉴权，使用鉴权管理器
                .anyExchange().access(accessManager)
                //鉴权的异常处理，权限不足，token失效
                .and().exceptionHandling()
                // 未登录访问时处理
                .authenticationEntryPoint(auroraAuthenticationEntryPoint)
                // 访问被拒绝时处理，没有权限访问
                .accessDeniedHandler(auroraAccessDeniedHandler)
                .and()
                // 跨域过滤器
                .addFilterAt(corsWebFilter(), SecurityWebFiltersOrder.CORS)
                //token的认证过滤器，用于校验token和认证
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1.配置跨域
        //允许哪种请求头跨域
        corsConfiguration.addAllowedHeader("*");
        //允许哪种方法类型跨域 get post delete put
        corsConfiguration.addAllowedMethod("*");
        // 允许哪些请求源跨域
        corsConfiguration.addAllowedOrigin("*");
        // 是否携带cookie跨域
        corsConfiguration.setAllowCredentials(true);

        //允许跨域的路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
