package xyz.xcye.auth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import xyz.xcye.auth.model.SecurityUserDetails;

import java.util.LinkedHashMap;

/**
 * jwt令牌增强，将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
 */
public class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {

    /**
     * 增强jwt，需要在此方法中
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 获取查询到的用户信息
        SecurityUserDetails user = (SecurityUserDetails) authentication.getUserAuthentication().getPrincipal();
        // 将额外的信息放入hashmap中
        LinkedHashMap<String, Object> extendInformation = new LinkedHashMap<>();
        // 添加userUid
        extendInformation.put("userUid", user.getUserUid());
        extendInformation.put("avatar", user.getAvatar());
        extendInformation.put("gender", user.getGender());
        extendInformation.put("nickname", user.getNickname());
        extendInformation.put("username", user.getUsername());
        extendInformation.put("verifyEmail", user.getVerifyEmail());
        // 将extendInformation添加到额外的信息中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
        return super.enhance(accessToken, authentication);
    }
}
