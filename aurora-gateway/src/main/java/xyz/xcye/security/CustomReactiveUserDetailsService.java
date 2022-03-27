package xyz.xcye.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.error("CustomReactiveUserDetailsService执行");
        String encode = passwordEncoder.encode("123456");
        return Mono.just(User.withUserDetails(new User("aurora",encode,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))).build());
    }
}
