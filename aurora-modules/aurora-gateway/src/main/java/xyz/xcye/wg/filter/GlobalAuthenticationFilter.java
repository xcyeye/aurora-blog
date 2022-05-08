package xyz.xcye.wg.filter;

import cn.hutool.core.codec.Base64;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.wg.util.SecurityResultHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    /**
     * JWT令牌的服务
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();

        //灰度发布拦截
        //interceptGray(exchange);

        //1、白名单放行，比如授权服务、静态资源.....
        if (checkUrls(Arrays.asList("/","/admin/user"),requestUrl)){
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
            String jti=additionalInformation.get("jti").toString();
            /** 查看黑名单中是否存在这个jti，如果存在则这个令牌不能用 ****/
            /*Boolean hasKey = stringRedisTemplate.hasKey("OAuthConstant.JTI_KEY_PREFIX" + jti);
            if (Objects.requireNonNull(hasKey))
                return invalidTokenMono(exchange);*/
            //取出用户身份信息
            String user_name = additionalInformation.get("user_name").toString();
            //获取用户权限
            List<String> authorities = (List<String>) additionalInformation.get("authorities");
            //从additionalInformation取出userId
            /*String userId = additionalInformation.get(OAuthConstant.USER_ID).toString();
            Integer gender = (Integer) additionalInformation.get(OAuthConstant.GENDER);
            String nickName = additionalInformation.get(OAuthConstant.NICK_NAME).toString();
            String avatar = additionalInformation.get(OAuthConstant.AVATAR).toString();
            String mobile = additionalInformation.get(OAuthConstant.MOBILE).toString();
            String email = additionalInformation.get(OAuthConstant.EMAIL).toString();*/

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("OAuthConstant.PRINCIPAL_NAME", user_name);
            /*jsonObject.put(OAuthConstant.PRINCIPAL_NAME, user_name);
            jsonObject.put(OAuthConstant.AUTHORITIES_NAME,authorities);
            //过期时间，单位秒
            jsonObject.put(OAuthConstant.EXPR,oAuth2AccessToken.getExpiresIn());
            jsonObject.put(OAuthConstant.JTI,jti);
            //封装到JSON数据中
            jsonObject.put(OAuthConstant.USER_ID, userId);
            jsonObject.put(OAuthConstant.GENDER, gender);
            jsonObject.put(OAuthConstant.NICK_NAME, nickName);
            jsonObject.put(OAuthConstant.AVATAR, avatar);
            jsonObject.put(OAuthConstant.MOBILE, mobile);
            jsonObject.put(OAuthConstant.EMAIL, email);*/

            //将解析后的token加密放入请求头中，方便下游微服务解析获取用户信息
            String base64 = Base64.encode(jsonObject.toJSONString());
            //放入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("OAuthConstant.TOKEN_NAME", base64).build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        } catch (InvalidTokenException e) {
            //解析token异常，直接返回token无效
            return invalidTokenMono(exchange);
        }
    }

    /**
     * 灰度发布的拦截标记
     */
    /*private void interceptGray(ServerWebExchange exchange){
        *//**
         * TODO 此处通过请求头进行灰度标记
         * 特定的场景：比如根据登录的用户某种信息进行灰度标记，只需要对用户校验，然后加上灰度标记
         *//*
        //解析请求头，查看是否存在灰度发布的请求头信息，如果存在则将其放置在ThreadLocal中
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (headers.containsKey(GrayConstant.GRAY_HEADER)){
            String gray = headers.getFirst(GrayConstant.GRAY_HEADER);
            if (StrUtil.equals(gray,GrayConstant.GRAY_VALUE)){
                RibbonRequestContextHolder.put(GrayConstant.GRAY_HEADER, GrayConstant.GRAY_VALUE);
            }
        }
    }*/

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
        String tokenStr = exchange.getRequest().getHeaders().getFirst("Authorization");
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