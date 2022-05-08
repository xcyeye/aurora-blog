package xyz.xcye.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import xyz.xcye.auth.enums.TokenConstant;
import xyz.xcye.auth.service.JwtTokenUserDetailsService;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:49
 */

@Configuration
public class AccessTokenConfig {

    @Autowired
    private JwtTokenUserDetailsService userDetailsService;

    private static final String signingKey = "aurora";

    /**
     * 令牌存储模式
     * @return
     */
    @Bean
    public TokenStore tokenStore() {

        return new JwtTokenStore(jwtAccessTokenConverter());
        //return new RedisTokenStore(factory);
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenEnhancer jwtAccessTokenEnhancer = new JwtAccessTokenEnhancer();

        // 设置秘钥
        jwtAccessTokenEnhancer.setSigningKey(TokenConstant.SIGN_KEY);
        return jwtAccessTokenEnhancer;
    }
}

