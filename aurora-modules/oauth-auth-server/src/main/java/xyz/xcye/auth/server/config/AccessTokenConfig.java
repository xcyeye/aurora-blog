package xyz.xcye.auth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import xyz.xcye.auth.service.JwtTokenUserDetailsService;
import xyz.xcye.auth.service.SecurityUser;

import java.util.LinkedHashMap;

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
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenEnhancer jwtAccessTokenEnhancer = new JwtAccessTokenEnhancer();

        // 设置秘钥
        jwtAccessTokenEnhancer.setSigningKey(signingKey);
        return jwtAccessTokenEnhancer;
    }
}

/**
 * jwt令牌增强，将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
 */
class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {

    /**
     * 增强jwt，需要在此方法中
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 获取查询到的用户信息
        SecurityUser user = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
        // 将额外的信息放入hashmap中
        LinkedHashMap<String, Object> extendInformation = new LinkedHashMap<>();
        // 添加userUid
        extendInformation.put("userUid",user.getUserId());
        // 将extendInformation添加到额外的信息中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
        return super.enhance(accessToken, authentication);
    }
}