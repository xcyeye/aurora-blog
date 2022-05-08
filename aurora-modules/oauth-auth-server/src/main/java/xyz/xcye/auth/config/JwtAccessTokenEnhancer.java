package xyz.xcye.auth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import xyz.xcye.auth.model.SecurityUserDetails;
import xyz.xcye.core.constant.oauth.OauthJwtConstant;

import java.util.LinkedHashMap;

/**
 * jwt令牌增强，将业务所需的额外信息放入令牌中，这样下游微服务就能解析令牌获取
 * 也就是将额外的用户信息，放入到jwt中，解析jwt就可以拿到这些数据
 */
public class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {

    /**
     * 增强jwt，需要在此方法中
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
        extendInformation.put(OauthJwtConstant.USER_UID, user.getUserUid());
        extendInformation.put(OauthJwtConstant.NICKNAME, user.getNickname());
        extendInformation.put(OauthJwtConstant.USERNAME, user.getUsername());
        extendInformation.put(OauthJwtConstant.VERIFY_EMAIL, user.getVerifyEmail());

        // 将extendInformation添加到额外的信息中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extendInformation);
        return super.enhance(accessToken, authentication);
    }
}
