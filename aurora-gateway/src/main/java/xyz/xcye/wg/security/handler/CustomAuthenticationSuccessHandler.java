package xyz.xcye.wg.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.util.jwt.JwtUtils;
import xyz.xcye.wg.enums.TokenEnum;
import xyz.xcye.wg.util.SecurityResultHandler;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

/**
 * 自定义登录成功处理类
 * @author qsyyke
 */


@Component
public class CustomAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();

        //1. 获取用户名等信息
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();

        //用户名
        String username = (String) authentication.getPrincipal();

        //获取权限信息
        UserAccountDO userPermission = getPermission(grantedAuthorities);

        //从请求头中获取rememberMe的秒数
        Integer rememberMeSecond = getRememberMeSecond(exchange);

        //2. 获取一个token
        String token = JwtUtils.generateToken(TokenEnum.JWT_SUBJECT, username,
                rememberMeSecond, username, userPermission.getRole(), userPermission.getPermission(),
                TokenEnum.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        //3. 将此token放入header中
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(TokenEnum.JWT_HEADER_TOKEN_NAME,token);

        //将此token方法redis中
        redisTemplate.opsForValue().set(TokenEnum.JWT_HEADER_TOKEN_NAME + ":" + username,token,Duration.ofSeconds(rememberMeSecond));

        return SecurityResultHandler.success(webFilterExchange.getExchange(),authentication);
    }

    public Integer getRememberMeSecond(ServerWebExchange exchange) {
        //获取免登录天数
        String rememberMeDay = "";

        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        if (queryParams.get("remember-me-day") != null) {
            rememberMeDay = queryParams.get("remember-me-day").get(0);
        }

        //如果没有设置免登陆天数，则设置为半天
        rememberMeDay = rememberMeDay.equals("") ? "1" : rememberMeDay;
        //1000 * 60 * 60 * 24 一天的秒数
        int rememberMeDayNum = Integer.parseInt(rememberMeDay);

        return rememberMeDayNum * 60 * 60 * 24;
    }

    public UserAccountDO getPermission(List<GrantedAuthority> grantedAuthorities) {
        StringBuilder permission = new StringBuilder();
        String role = "";

        for (int i = 0; i < grantedAuthorities.size(); i++) {
            String authority = grantedAuthorities.get(i).getAuthority();

            if (authority.contains("ROLE_")) {
                role = authority;
            }else {
                permission.append(authority).append(",");
            }

            //去除最后一个,
            if (i == grantedAuthorities.size() -1) {
                permission = new StringBuilder(permission.substring(0, permission.length() - 1));
            }
        }

        UserAccountDO userPermission = UserAccountDO.builder().permission(String.valueOf(permission))
                .role(role).build();
        return userPermission;

    }

}
