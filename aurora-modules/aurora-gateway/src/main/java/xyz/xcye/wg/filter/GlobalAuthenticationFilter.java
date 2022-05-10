package xyz.xcye.wg.filter;

import cn.hutool.core.codec.Base64;
import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.admin.constant.RedisStorageConstant;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.core.constant.oauth.OauthJwtConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.wg.util.SecurityResultHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局过滤器，对token的拦截，解析token放入header中，便于下游微服务获取用户信息
 * 分为如下几步：
 *  1、白名单直接放行
 *  2、校验token
 *  3、读取token中存放的用户信息
 *  4、重新封装用户信息，加密成功json数据放入请求头中传递给下游微服务
 */
@Component
@Slf4j
public class GlobalAuthenticationFilter implements GlobalFilter {

    private final String authorizationName = "Authorization";

    /**
     * JWT令牌的服务
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();

        // 获取redis中的所有白名单
        List<WhiteUrl> whiteUrlList = (List<WhiteUrl>) redisTemplate.opsForValue().get(RedisStorageConstant.STORAGE_WHITE_URL_INFO);

        assert whiteUrlList != null;
        String[] whiteUrlArray = whiteUrlList.stream()
                .map(WhiteUrl::getUrl)
                .collect(Collectors.joining(","))
                .split(",");
        //1、白名单放行，比如授权服务、静态资源.....
        if (checkUrls(Arrays.asList(whiteUrlArray),requestUrl)){
            return chain.filter(exchange);
        }

        //2、 检查token是否存在
        String token = getToken(exchange);
        if (StringUtils.isBlank(token)) {
            return invalidTokenMono(exchange);
        }

        //3 判断是否是有效的token
        OAuth2AccessToken oAuth2AccessToken;
        try {
            //解析token，使用tokenStore
            oAuth2AccessToken = tokenStore.readAccessToken(token);
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            //令牌的唯一ID
            String jti = additionalInformation.get("jti").toString();
            // 查看黑名单中是否存在这个jti，如果存在则这个令牌不能用
            Boolean hasKey = redisTemplate.hasKey(jti);
            if (hasKey != null && hasKey) {
                return invalidTokenMono(exchange);
            }

            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");

            // 构建一个在下层服务中，传递的用户对象
            JwtUserInfo jwtUserInfo = JwtUserInfo.builder()
                    .nickname((String) additionalInformation.get(OauthJwtConstant.NICKNAME))
                    .username((String) additionalInformation.get(OauthJwtConstant.USERNAME))
                    .userUid((Long) additionalInformation.get(OauthJwtConstant.USER_UID))
                    .roleList(authorities)
                    .verifyEmail((Boolean) additionalInformation.get(OauthJwtConstant.VERIFY_EMAIL))
                    .build();

            //将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
            String base64 = Base64.encode(ConvertObjectUtils.jsonToString(jwtUserInfo));

            //放入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate()
                    .header(OauthJwtConstant.REQUEST_TOKEN_NAME, base64).build();
            exchange.getRequest().mutate().header(OauthJwtConstant.REQUEST_JWT_TOKEN_NAME, token);
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        } catch (InvalidTokenException e) {
            //解析token异常，直接返回token无效
            return invalidTokenMono(exchange);
        }
    }

    /**
     * 对url进行校验匹配
     */
    private boolean checkUrls(List<String> urls,String path){
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String url : urls) {
            if (pathMatcher.match(url,path))
                return true;
        }
        return false;
    }

    /**
     * 从请求头中获取Token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst(authorizationName);
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange) {
        return SecurityResultHandler.failure(exchange,
                ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getMessage(),
                ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getCode());
    }

}