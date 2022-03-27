package xyz.xcye.security;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.xcye.entity.table.Permission;
import xyz.xcye.service.PermissionService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 自定义鉴权逻辑处理类 在这里判断用户的账户是否过期等等操作,这个类的执行，在登录的时候，不会执行，只有登录成功或者没有登录的时候，进行鉴权
 * <p>最终如果返回new AuthorizationDecision(true)，则鉴权成功</p>
 * <p>返回new AuthorizationDecision(false)，则鉴权失败</p>
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private PermissionService permissionService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> contextAuthentication, AuthorizationContext authorizationContext) {

        log.error("CustomReactiveAuthorizationManager执行-自定义鉴权逻辑处理类");

        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request = exchange.getRequest();

        PathContainer pathContainer = request.getPath().pathWithinApplication();

        //访问的路径 uri，不包含host 此uri需要验证
        String needVerifyUrl = pathContainer.value();

        //获取SecurityContext对象
        SecurityContext securityContext = SecurityContextHolder.getContext();
        //从上下文中获取Authentication对象，如果没有，返回null，在xyz.xcye.security.CustomReactiveAuthenticationManager中被放入上下文中
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            //鉴权失败
            return Mono.just(new AuthorizationDecision(false));
        }

        //1.从数据库中，查询所有的url需要的权限信息 然后逐一和当前的uri进行匹配
        List<Permission> permissions = permissionService.queryAllRole();
        for (Permission permission : permissions) {

            //需要某个角色或者权限访问的路径
            String permissionPath = permission.getPath();

            //此permissionPath路径需要什么角色
            String role = permission.getRole();

            //此permissionPath路径需要什么权限
            String[] permissionArr = permission.getPermission().split(",");

            //只要角色和权限满足其中一个就行，但是优先角色
            boolean match = antPathMatcher.match(needVerifyUrl, permissionPath);
            if (match) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }


        //验证权限 设置为true，就是鉴权成功
        return Mono.just(new AuthorizationDecision(false));

    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }


}
