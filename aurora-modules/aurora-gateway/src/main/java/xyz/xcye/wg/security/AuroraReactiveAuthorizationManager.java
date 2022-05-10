package xyz.xcye.wg.security;


import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.xcye.admin.constant.RedisStorageConstant;
import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.core.constant.oauth.OauthJwtConstant;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 鉴权管理器
 * 自定义鉴权逻辑处理类 在这里判断用户的账户是否过期等等操作,这个类的执行，在登录的时候，不会执行，只有登录成功或者没有登录的时候，进行鉴权
 * <p>最终如果返回new AuthorizationDecision(true)，则鉴权成功</p>
 * <p>返回new AuthorizationDecision(false)，则鉴权失败</p>
 * @author qsyyke
 */

@Component
public class AuroraReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> contextAuthentication, AuthorizationContext authorizationContext) {

        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();

        // 获取请求方法
        String method = request.getMethodValue();
        // 获取请求的uri
        URI uri = request.getURI();

        // 将当前的请求方法和uri组装成一个restFul风格的地址
        String restFulPath = method + ":" + uri.getPath();

        // 获取redis中的所有角色权限关系
        Set<Map<String, RolePermissionDTO>> allRolePermissionSet = null;
        try {
            allRolePermissionSet = (Set<Map<String, RolePermissionDTO>>) redisTemplate.opsForValue().get(RedisStorageConstant.STORAGE_ROLE_PERMISSION_INFO);
        } catch (Exception e) {
            // 如果redis中没有角色权限关系信息，直接返回鉴权失败 为了保护系统
            return Mono.just(new AuthorizationDecision(false));
        }

        if (allRolePermissionSet == null) {
            // 如果redis中没有角色权限关系信息，直接返回鉴权失败 为了保护系统
            return Mono.just(new AuthorizationDecision(false));
        }

        // 获取所有的角色
        List<String> roleList = new ArrayList<>();
        allRolePermissionSet.forEach(rolePermissionMap -> {
            // 将能够和restFulPath匹配的角色放入角色集合中
            rolePermissionMap.forEach((roleName, rolePermissionDTO) -> {
                if (antPathMatcher.match(rolePermissionDTO.getPath() ,restFulPath)) {
                    roleList.add(roleName);
                }
            });
        });

        return contextAuthentication
                // 是否认证成功
                .filter(Authentication::isAuthenticated)
                // 获取认证后的全部权限
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                //如果权限包含则判断为true
                .any(authority->{
                    // 超级管理员直接放行
                    if (OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME.equals(authority))
                        return true;
                    //其他必须要判断角色是否存在交集
                    return CollectionUtil.isNotEmpty(roleList) && roleList.contains(authority);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

    }
}
