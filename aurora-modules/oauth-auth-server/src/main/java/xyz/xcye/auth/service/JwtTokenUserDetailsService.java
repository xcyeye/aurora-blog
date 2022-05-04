package xyz.xcye.auth.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 14:17
 */


@Service
public class JwtTokenUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //List<String> roles = Objects.requireNonNull(roleResult.getData()).stream().map(sysRole -> OAuthConstant.ROLE_PREFIX+sysRole.getCode()).collect(Collectors.toList());
        return SecurityUser.builder()
                .nickname("青衫烟雨客")
                .gender(1)
                .avatar("https://www.xcye.xyz/avatar.png")
                .mobile("13638857372")
                .email("2291308094@qq.com")
                .userId("2375354234")
                .username("qsyyke")
                .password("123456")
                //将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList("ROLE_admin","ROLE_user"))
                .build();

    }
}
