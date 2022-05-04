package xyz.xcye.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import xyz.xcye.auth.server.exception.OAuthServerAuthenticationEntryPoint;
import xyz.xcye.auth.server.exception.OAuthServerClientCredentialsTokenEndpointFilter;
import xyz.xcye.auth.server.exception.OAuthServerWebResponseExceptionTranslator;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:50
 */

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 令牌存储策略
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 客户端存储策略，这里使用内存方式，后续可以存储在数据库
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * Security的认证管理器，密码模式需要用到
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private OAuthServerAuthenticationEntryPoint authenticationEntryPoint;

    /*@Autowired
    private DataSource dataSource;*/

    /**
     * 授权码模式的service，使用授权码模式authorization_code必须注入
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        //return new JdbcAuthorizationCodeServices(dataSource);
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 令牌管理服务的配置
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        //客户端端配置策略
        services.setClientDetailsService(clientDetailsService);
        //支持令牌的刷新
        services.setSupportRefreshToken(true);
        //令牌服务
        services.setTokenStore(tokenStore);
        //access_token的过期时间
        services.setAccessTokenValiditySeconds(60 * 60 * 24 * 3);
        //refresh_token的过期时间
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);

        //设置令牌增强，使用JwtAccessTokenConverter进行转换
        services.setTokenEnhancer(jwtAccessTokenConverter);
        return services;
    }

    /**
     * 配置令牌访问的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //自定义ClientCredentialsTokenEndpointFilter，用于处理客户端id，密码错误的异常
        OAuthServerClientCredentialsTokenEndpointFilter endpointFilter = new OAuthServerClientCredentialsTokenEndpointFilter(security,authenticationEntryPoint);
        endpointFilter.afterPropertiesSet();
        security.addTokenEndpointAuthenticationFilter(endpointFilter);

        security
                //.authenticationEntryPoint(authenticationEntryPoint)
                //开启/oauth/token_key验证端口权限访问
                .tokenKeyAccess("permitAll()")
                //开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("permitAll()");
                //一定不要添加allowFormAuthenticationForClients，否则自定义的OAuthServerClientCredentialsTokenEndpointFilter不生效
        //.allowFormAuthenticationForClients();
    }

    /**
     * 客户端详情配置，例如秘钥，唯一id
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 指定客户端唯一id
                .withClient("aurora")
                // 指定客户端秘钥，使用算法进行加密
                .secret(new BCryptPasswordEncoder().encode("123"))
                // 资源id，唯一，可以指定多个
                .resourceIds("res1")
                // 定义认证中心支持的授权模式
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                // 允许的授权范围，可以自定义，资源服务可以根据这个权限进行鉴权
                .scopes("all")
                // 是否需要授权，设置为false则不需要用户点击确认授权直接返回授权码
                .autoApprove(false)
                // 跳转的地址
                .redirectUris("https://aurora.xcye.xyz");
    }

    /**
     * 配置令牌访问的端点
     */
    @Override
    @SuppressWarnings("ALL")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //设置异常WebResponseExceptionTranslator，用于处理用户名，密码错误、授权类型不正确的异常
                .exceptionTranslator(new OAuthServerWebResponseExceptionTranslator())
                //授权码模式所需要的authorizationCodeServices
                .authorizationCodeServices(authorizationCodeServices())
                //密码模式所需要的authenticationManager
                .authenticationManager(authenticationManager)
                //令牌管理服务，无论哪种模式都需要
                .tokenServices(tokenServices())
                //只允许POST提交访问令牌，uri：/oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);
    }
}
