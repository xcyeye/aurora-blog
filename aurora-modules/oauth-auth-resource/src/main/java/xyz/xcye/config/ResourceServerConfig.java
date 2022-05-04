package xyz.xcye.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import xyz.xcye.exception.OAuthResourceAuthenticationEntryPoint;
import xyz.xcye.exception.RequestAccessDeniedHandler;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 10:37
 */

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private OAuthResourceAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private RequestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public ResourceServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        // 配置令牌存储策略，使用配置的jwt存储
        services.setTokenStore(tokenStore);
        // 令牌的增强转换器
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }

    /**
     * 配置资源id和令牌校验服务
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置唯一资源id
        resources.resourceId("res1")
                // 设置令牌失效
                .authenticationEntryPoint(authenticationEntryPoint)

                // 自定义权限不足处理
                .accessDeniedHandler(accessDeniedHandler)
                // 配置令牌校验服务
                .tokenServices(tokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .anyRequest().authenticated();
    }
}
