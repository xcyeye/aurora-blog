package xyz.xcye.wg.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import xyz.xcye.wg.security.CustomReactiveAuthorizationManager;
import xyz.xcye.wg.security.convert.CustomAuthenticationConverter;
import xyz.xcye.wg.security.handler.CustomAccessDeniedHandler;
import xyz.xcye.wg.security.handler.CustomAuthenticationEntryPoint;
import xyz.xcye.wg.security.handler.CustomAuthenticationFailureHandler;
import xyz.xcye.wg.security.handler.CustomAuthenticationSuccessHandler;


/**
 * @author qsyyke
 */

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    /** 自定义鉴权逻辑处理类 **/
    @Autowired
    private CustomReactiveAuthorizationManager customReactiveAuthorizationManager;

    /** 自定义认证失败处理类 **/
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /** 自定义认证成功处理类 **/
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /** 自定义未登录访问需要认证的资源时的处理类 **/
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /** 自定义访问被拒绝时处理类 **/
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /** 自定义登录参数转换器 **/
    @Autowired
    private CustomAuthenticationConverter customAuthenticationConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable();

        //设置登录url，并不是登录页面
        http.formLogin().loginPage("/login")
            //设置认证失败处理类
            .authenticationFailureHandler(customAuthenticationFailureHandler)
            //设置认证成功处理类
            .authenticationSuccessHandler(customAuthenticationSuccessHandler);

        //设置请求
        http.authorizeExchange()
                //对/login路径放行
                .pathMatchers("/login").permitAll()
                //对所有的get请求放行
                .pathMatchers(HttpMethod.GET).permitAll()
                //自定义鉴权逻辑处理类
                .anyExchange().access(customReactiveAuthorizationManager);

        //设置异常
        http.exceptionHandling()
                //未登录访问资源时处理类
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and().
                exceptionHandling().
                //访问被拒绝时处理类
                accessDeniedHandler(customAccessDeniedHandler);

        //构建SecurityWebFilterChain
        SecurityWebFilterChain build = http.csrf().disable().build();

        //自定义参数转换
        build.getWebFilters()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .subscribe(webFilter -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
                    filter.setServerAuthenticationConverter(customAuthenticationConverter);
                });

        return build;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
