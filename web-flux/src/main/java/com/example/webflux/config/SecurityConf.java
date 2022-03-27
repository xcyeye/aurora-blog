package com.example.webflux.config;

import com.example.webflux.operation.CustomServerAccessDeniedHandler;
import com.example.webflux.operation.CustomServerAuthenticationEntryPoint;
import com.example.webflux.operation.CustomServerAuthenticationFailureHandler;
import com.example.webflux.operation.CustomServerAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * @author qsyyke
 */

@EnableWebFluxSecurity
public class SecurityConf {


    @Autowired
    private CustomReactiveAuthorizationManager customReactiveAuthorizationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http.csrf().disable();

        http.formLogin().loginPage("/aurora")
                        .authenticationFailureHandler(new CustomServerAuthenticationFailureHandler())
                                .authenticationSuccessHandler(new CustomServerAuthenticationSuccessHandler());

        http.authorizeExchange().pathMatchers("/aurora").permitAll().anyExchange()
                .access(customReactiveAuthorizationManager);

        http.exceptionHandling().authenticationEntryPoint(new CustomServerAuthenticationEntryPoint())
                        .and().exceptionHandling().accessDeniedHandler(new CustomServerAccessDeniedHandler());

        SecurityWebFilterChain build = http.csrf().disable().build();

        Disposable subscribe = build.getWebFilters()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .subscribe(webFilter -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
                    filter.setServerAuthenticationConverter(new CustomServerAuthenticationConverter());
                });

        return build;



        /*SecurityWebFilterChain chain = http.formLogin()
                .loginPage("/qsyyke")
                // 登录成功handler
                .authenticationSuccessHandler(new CustomServerAuthenticationSuccessHandler())
                // 登陆失败handler
                .authenticationFailureHandler(new CustomServerAuthenticationFailureHandler())
                // 无访问权限handler
                .authenticationEntryPoint(new CustomServerAuthenticationEntryPoint())
                .and()
                .logout()
                // 登出成功handler
                .logoutSuccessHandler((exchange, authentication) -> {
                    System.out.println("登出成功");
                    return Mono.empty();
                })
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authorizeExchange()
                // 白名单放行
                .pathMatchers("/qsyyke").permitAll()
                // 访问权限控制
                .anyExchange().access(customReactiveAuthorizationManager)
                .and().build();*/

        // 设置自定义登录参数转换器
        /*chain.getWebFilters()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .subscribe(webFilter -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
                    filter.setServerAuthenticationConverter(new CustomServerAuthenticationConverter());
                });*/


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
