package xyz.xcye.wg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.wg.service.CustomUserDetailsService;

/**
 * 当用户进行登录操作之后，首先会进入自定义参数转换器，然后就会到这个类中，验证用户的身份信息，还不进行身份权限认证，权限认证是在check()方法中
 * @author qsyyke
 */

@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        //获取输入的用户名
        String username = authentication.getName();

        //获取输入的明文
        String presentedPassword = (String) authentication.getCredentials();

        //这里从数据库中获取真正的密码
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        //这里不用验证userDetails对象是否为null，因为已经在loadUserByUsername()方法中验证过了

        //将数据库中查询到的密码和用户输入的明文密码调用matches方法进行比较
        if (!passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
            return Mono.error(new AuthenticationCredentialsNotFoundException(username + ResponseStatusCodeEnum.PERMISSION_USER_MISTAKE.getMessage()));
        }

        //密码正确，检查账户的状态
        return checkAccount(userDetails);
    }

    /**
     * 检查账户的状态，是否禁用，是否过期，凭证是否过期等等
     * @param userDetails 对象
     * @return 异常或者null
     */
    private Mono<Authentication> checkAccount(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            return Mono.error(new DisabledException(userDetails.getUsername() + ResponseStatusCodeEnum.PERMISSION_USER_IS_DISABLE.getMessage()));
        }

        if (!userDetails.isAccountNonLocked()) {
            return Mono.error(new LockedException(userDetails.getUsername() + ResponseStatusCodeEnum.PERMISSION_USER_IS_LOCKED.getMessage()));
        }

        if (!userDetails.isAccountNonExpired()) {
            return Mono.error(new AccountExpiredException(userDetails.getUsername() + ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getMessage()));
        }

        if (!userDetails.isCredentialsNonExpired()) {
            return Mono.error(new CredentialsExpiredException(userDetails.getUsername() + ResponseStatusCodeEnum.PERMISSION_TOKEN_EXPIRATION.getMessage()));
        }

        //运行到这里说明用户的密码正确
        Authentication hadAuthentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

        //WebFlux方式默认没有放到context中，需要手动放入
        SecurityContextHolder.getContext().setAuthentication(hadAuthentication);
        return Mono.just(hadAuthentication);
    }
}
