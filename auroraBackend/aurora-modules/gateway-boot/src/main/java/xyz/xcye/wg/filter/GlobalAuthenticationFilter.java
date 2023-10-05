package xyz.xcye.wg.filter;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.auth.constant.AuthRedisConstant;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.core.dto.JwtUserInfo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.wg.util.SecurityResultHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 全局过滤器，对token的拦截，解析token放入header中，便于下游微服务获取用户信息
 * 分为如下几步：
 * 1、白名单直接放行
 * 2、校验token
 * 3、读取token中存放的用户信息
 * 4、重新封装用户信息，加密成功json数据放入请求头中传递给下游微服务
 */
@Component
@Slf4j
public class GlobalAuthenticationFilter implements GlobalFilter {

    private final String authorizationName = "Authorization";

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * JWT令牌的服务
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求方法
        String method = exchange.getRequest().getMethodValue();
        // 获取请求的uri
        URI uri = exchange.getRequest().getURI();

        // 将当前的请求方法和uri组装成一个restFul风格的地址
        String restFulPath = method + ":" + uri.getPath();

        // 如果是登录处理的地址，并且请求头中存在token，并且该token没有失效，那么就退出
        String loginProcessUrl = "POST:" + OauthJwtConstant.LOGIN_PROCESS_URL;

        if (loginProcessUrl.equals(restFulPath) && loginStatus(exchange)) {
            return invalidTokenMono(exchange, ResponseStatusCodeEnum.PERMISSION_USER_HAD_LOGIN);
        }

        if (loginProcessUrl.equals(restFulPath) && rememberMe(exchange)) {
            return invalidTokenMono(exchange, ResponseStatusCodeEnum.PERMISSION_USER_HAD_LOGIN);
        }

        // 检查白名单
        if (checkWhiteUrlStatus(exchange, chain)) {
            return chain.filter(exchange);
        }

        // 2、 检查token是否存在
        String token = getToken(exchange);
        if (!StringUtils.hasLength(token)) {
            return invalidTokenMono(exchange, ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        return storageUserJwtInfo(exchange, token, chain);
    }

    /**
     * 从请求头中获取Token
     */
    private String getToken(ServerWebExchange exchange) {
        String tokenStr = exchange.getRequest().getHeaders().getFirst(authorizationName);
        if (!StringUtils.hasLength(tokenStr)) {
            return null;
        }
        String token = tokenStr.split(" ")[1];
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        return token;
    }

    /**
     * 无效的token
     */
    private Mono<Void> invalidTokenMono(ServerWebExchange exchange, ResponseStatusCodeEnum statusCodeEnum) {
        return SecurityResultHandler.failure(exchange, statusCodeEnum);
    }

    private boolean rememberMe(ServerWebExchange exchange) {
        String token = getToken(exchange);
        if (!StringUtils.hasLength(token)) {
            return false;
        }

        // 判断token是否失效
        OAuth2AccessToken oAuth2AccessToken = effectiveToken(token);
        return oAuth2AccessToken != null;
    }

    private boolean loginStatus(ServerWebExchange exchange) {
        // 获取cookie
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        AtomicReference<String> cookieValueAtomicReference = new AtomicReference<>("");
        cookies.forEach((cookieName, cookieValues) -> {
            if (OauthJwtConstant.COOKIE_STORAGE_LOGIN_SUCCESS_STATUS.equals(cookieName)) {
                cookieValueAtomicReference.set(cookieValues.size() > 0 ? cookieValues.get(0).getValue() : null);
            }
        });
        String cookieValue = cookieValueAtomicReference.get();
        if (!StringUtils.hasLength(cookieValue)) {
            // 没有cookie或者为空，没有登录
            return false;
        }

        // 获取过期时间 用户名:cookie到期时间:秘钥组成
        String decodeStr = Base64.decodeStr(cookieValue);
        String[] cookieArr = decodeStr.split(":");
        String username = cookieArr[0];
        long cookieExpiryTime = Long.parseLong(cookieArr[1]);
        String cookieSecreyKey = cookieArr[2];

        // 判断秘钥是否正确
        if (!OauthJwtConstant.STORAGE_COOKIE_SECRET_KEY.equals(cookieSecreyKey)) {
            return false;
        }

        // 判断cookie是否过期
        return System.currentTimeMillis() < cookieExpiryTime;
    }

    /**
     * 判断token是否有效，true有效，反之
     *
     * @param token
     * @return
     */
    private OAuth2AccessToken effectiveToken(String token) {
        OAuth2AccessToken oAuth2AccessToken;
        // 解析token，使用tokenStore
        try {
            oAuth2AccessToken = tokenStore.readAccessToken(token);
        } catch (Exception e) {
            return null;
        }
        return oAuth2AccessToken;
    }

    /**
     * 检查白名单状态，如果为true，则表示是白名单
     *
     * @param exchange 对象
     * @return true是白名单
     */
    private boolean checkWhiteUrlStatus(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> list = exchange.getRequest().getHeaders().get(RequestConstant.REQUEST_WHITE_URL_STATUS);
        // 如果是白名单，则直接放行
        if (list != null && "true".equals(list.get(0))) {
            String token = getToken(exchange);
            // 如果token不为null，则存储用户信息
            if (token != null) {
                try {
                    storageUserJwtInfo(exchange, token, chain);
                    // 发生异常，不做处理
                } catch (Exception e) {
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 存储用户信息
     *
     * @param exchange 对象
     * @param token    jwt
     * @param chain    对象
     * @return
     */
    private Mono<Void> storageUserJwtInfo(ServerWebExchange exchange, String token, GatewayFilterChain chain) {
        OAuth2AccessToken oAuth2AccessToken = effectiveToken(token);
        if (oAuth2AccessToken == null) {
            return invalidTokenMono(exchange, ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();

        // 令牌的唯一ID
        String jti = additionalInformation.get("jti").toString();

        // 查看黑名单中是否存在这个jti，如果存在则这个令牌不能用
        Boolean hasKey = redisTemplate.hasKey(AuthRedisConstant.STORAGE_JWT_BLACKLIST_PREFIX + jti);
        if (hasKey != null && hasKey) {
            return invalidTokenMono(exchange, ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION);
        }

        HashMap<String, Object> userinfoMap = (HashMap<String, Object>) additionalInformation.get(OauthJwtConstant.USERINFO);

        // 获取用户权限
        List<String> authorities = (List<String>) userinfoMap.get(OauthJwtConstant.AUTHORITY);

        // 构建一个在下层服务中，传递的用户对象
        JwtUserInfo jwtUserInfo = JwtUserInfo.builder()
                .nickname((String) userinfoMap.get(OauthJwtConstant.NICKNAME))
                .username((String) userinfoMap.get(OauthJwtConstant.USERNAME))
                .userUid(Long.parseLong((String) userinfoMap.get(OauthJwtConstant.USER_UID)))
                .roleList(authorities)
                .verifyEmail((Boolean) userinfoMap.get(OauthJwtConstant.VERIFY_EMAIL))
                .jwtToken(token)
                .build();

        // 将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
        String base64 = Base64.encode(ConvertObjectUtils.jsonToString(jwtUserInfo));

        // 放入请求头中
        ServerHttpRequest tokenRequest = exchange.getRequest().mutate()
                .header(RequestConstant.REQUEST_TOKEN_NAME, base64).build();
        exchange.mutate().request(tokenRequest);
        exchange.getRequest().mutate().header(RequestConstant.REQUEST_WHITE_URL_FLAG_NAME, "false");
        return chain.filter(exchange);
    }

}