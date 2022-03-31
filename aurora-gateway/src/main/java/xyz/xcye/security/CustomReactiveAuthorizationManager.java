package xyz.xcye.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.common.annotaion.Log;
import xyz.xcye.common.entity.JwtEntity;
import xyz.xcye.common.entity.table.VerifyPath;
import xyz.xcye.common.enums.ResultStatusCode;
import xyz.xcye.enums.TokenEnum;
import xyz.xcye.service.VerifyPathService;
import xyz.xcye.common.util.jwt.JwtUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 自定义鉴权逻辑处理类 在这里判断用户的账户是否过期等等操作,这个类的执行，在登录的时候，不会执行，只有登录成功或者没有登录的时候，进行鉴权
 * <p>最终如果返回new AuthorizationDecision(true)，则鉴权成功</p>
 * <p>返回new AuthorizationDecision(false)，则鉴权失败</p>
 * @author qsyyke
 */

@Component
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private VerifyPathService verifyPathService;

    @Log
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> contextAuthentication, AuthorizationContext authorizationContext) {
        /**
         * 1. 从请求头中获取token
         * 2. 从数据库中查询所有需要不同权限，不同角色才能访问的路径
         * 3. 逐一和当前uri进行匹配
         * 4. 如果匹配完全部之后，没有和当前uri匹配的路径，则表示不需要授权才能访问，获取token，如果token为null，则返回提示登录
         * 5. 如果有匹配成功的，则解析当前的token是否失效，失效返回失败
         * 6. 如果没有token没有失效，则从token中获取当前用户的权限，看是否有权限访问目标url
         * 7. 没有权限，
         */

        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();

        //从请求头中获取token
        String token = getTokenFromHeader(request);

        PathContainer pathContainer = request.getPath().pathWithinApplication();

        //访问的路径 uri，不包含host 此uri需要验证
        String needVerifyUrl = pathContainer.value();

        VerifyPath matchedVerifyPath = null;

        //1.从数据库中，查询所有的url需要的权限信息 然后逐一和当前的uri进行匹配
        List<VerifyPath> verifyPaths = verifyPathService.queryAllVerifyPath();
        for (VerifyPath verifyPath : verifyPaths) {
            //需要某个角色或者权限访问的路径
            String permissionPath = verifyPath.getPath();

            if (antPathMatcher.match(needVerifyUrl, permissionPath)) {
                matchedVerifyPath = verifyPath;
                break;
            }
        }

        JwtEntity jwtEntity = parseToken(token);

        //matchedVerifyPath 当前uri不需要特定角色才能访问
        if (matchedVerifyPath == null) {
            //如果没有token的话，则返回请登录， 存在token，并且没有过期
            return getAuthorizationDecision(jwtEntity,exchange);
        }

        //判断用户当前用户是否拥有访问的权限
        return Mono.just(new AuthorizationDecision(hasRole(jwtEntity, matchedVerifyPath)));
    }

    public static Mono<AuthorizationDecision> getAuthorizationDecision(JwtEntity jwtEntity,ServerWebExchange exchange) {
        if (jwtEntity.isExpiration()) {
            //token已经过期
            //return Mono.error(new AccessDeniedException(ResultStatusCode.PERMISSION_DENIED.getMessage()));
            return Mono.error(new UsernameNotFoundException(ResultStatusCode.PERMISSION_TOKEN_EXPIRATION.getMessage()));
        }

        return Mono.just(new AuthorizationDecision(true));
    }

    /**
     * 解析token，返回一个jwt实体
     * @param token
     * @return
     */
    private static JwtEntity parseToken(String token) {
        JwtEntity jwtEntity = null;
        try {
            jwtEntity = JwtUtil.parseJwtToken(token, TokenEnum.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (RuntimeException e) {
            e.printStackTrace();
            jwtEntity = new JwtEntity("","","",null,null,"","",null,true);
        }

        return jwtEntity;
    }

    /**
     * 从请求头中获取token，并不做是否过期检测
     * @param request request对象
     * @return 如果请求头中没有token，则返回null，反之
     */
    private static String getTokenFromHeader(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String token = null;

        if (headers.get(TokenEnum.JWT_HEADER_TOKEN_NAME) != null) {
            token = headers.get(TokenEnum.JWT_HEADER_TOKEN_NAME).get(0);
        }
        return token;
    }

    /**
     * 验证当前用户是否拥有访问请求的权限
     * @param jwtEntity 当前用户的身份信息
     * @param matchedVerifyPath 数据库中存放的该uri所需要的权限信息
     * @return
     */
    public static boolean hasRole(JwtEntity jwtEntity,VerifyPath matchedVerifyPath) {
        if (matchedVerifyPath.getRole().equals(jwtEntity.getRole())) {
            return true;
        }

        if (matchedVerifyPath.getIsRole()) {
            //因为此path必须要拥有role的人才能访问，所以直接返回
            return false;
        }

        //验证权限 permissions数据库中需要的权限
        String[] permissions = matchedVerifyPath.getPermission().split(",");
        for (String permission : permissions) {

            //jwtEntityPermission是用户所拥有的，从token中获取的
            for (String jwtEntityPermission : jwtEntity.getPermissions()) {
                if (permission.equals(jwtEntityPermission)) {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }
}
