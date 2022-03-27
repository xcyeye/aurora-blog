package com.example.webflux.config;

import com.example.webflux.service.MyDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    //@Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.error("CustomReactiveAuthenticationManager执行");
        //获取输入的用户名
        String username = authentication.getName();
        //获取输入的明文
        String presentedPassword = (String) authentication.getCredentials();

        log.error(username+ ":" + presentedPassword);

        //这里从数据库中获取真正的密码

        MyDetailsService myDetailsService = new MyDetailsService();

        //真实的用户名为aurora，密码Wie123456
        UserDetails userDetails = myDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            return Mono.error(new DisabledException("没有该用户"));
        }

        /*if (!passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
            return Mono.error(new BadCredentialsException("密码错误"));
        }*/

        //信息正确
        //已经验证的对象
        Authentication hadAuthentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        //WebFlux方式默认没有放到context中，需要手动放入
        SecurityContextHolder.getContext().setAuthentication(hadAuthentication);

        return Mono.just(hadAuthentication);

    }
}
