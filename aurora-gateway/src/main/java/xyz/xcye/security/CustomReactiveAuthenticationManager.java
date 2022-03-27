package xyz.xcye.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.xcye.service.CustomUserDetailsService;

/**
 * 当用户进行登录操作之后，首先会进入自定义参数转换器，然后就会到这个类中，验证用户的身份信息，还不进行身份权限认证，权限认证是在check()方法中
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.error("CustomReactiveAuthenticationManager执行");

        //获取输入的用户名
        String username = authentication.getName();

        //获取输入的明文
        String presentedPassword = (String) authentication.getCredentials();

        //这里从数据库中获取真正的密码
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            return Mono.error(new DisabledException("没有该用户"));
        }

        //将数据库中查询到的密码和用户输入的明文密码调用matches方法进行比较
        if (!passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
            return Mono.error(new BadCredentialsException(username + "的密码错误"));
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
            return Mono.error(new DisabledException("该账户已被禁用"));
        }

        if (!userDetails.isAccountNonLocked()) {
            return Mono.error(new LockedException("该账户已被锁定"));
        }

        if (!userDetails.isAccountNonExpired()) {
            return Mono.error(new AccountExpiredException("该账户已过期"));
        }

        if (!userDetails.isCredentialsNonExpired()) {
            return Mono.error(new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录"));
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
