package xyz.xcye.wg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.wg.model.SecurityUserDetails;

import java.util.LinkedHashMap;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 09:49
 */

@Configuration
public class AccessTokenConfig {

    @Value("${aurora.oauth.secret-key:xxxxx}")
    private String secretKey;

    /**
     * 令牌的存储策略，因为认证中心的令牌存储方式为jwt，这里也需要一样
     */
    @Bean
    public TokenStore tokenStore() {
        //使用JwtTokenStore生成JWT令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * JwtAccessTokenConverter
     * TokenEnhancer的子类，在JWT编码的令牌值和OAuth身份验证信息之间进行转换。
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenEnhancer();
        // 设置秘钥
        converter.setSigningKey(secretKey);
        return converter;
    }

    /**
     * JWT令牌增强，继承JwtAccessTokenConverter
     * 将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
     */
    public static class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {
        /**
         * 重写enhance方法，在其中扩展
         */
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            // 获取查询到的用户信息
            SecurityUserDetails user = (SecurityUserDetails) authentication.getUserAuthentication().getPrincipal();
            // 将额外的信息放入hashmap中
            LinkedHashMap<String, Object> extendInformation = new LinkedHashMap<>();
            // 添加userUid
            extendInformation.put(OauthJwtConstant.USER_UID, user.getUserUid());
            extendInformation.put(OauthJwtConstant.NICKNAME, user.getNickname());
            extendInformation.put(OauthJwtConstant.USERNAME, user.getUsername());
            extendInformation.put(OauthJwtConstant.VERIFY_EMAIL, user.getVerifyEmail());

            // 将extendInformation添加到额外的信息中
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
            return super.enhance(accessToken, authentication);
        }
    }
}

