package xyz.xcye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.xcye.entity.table.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 在此类中，根据用户名，向数据库中查询正确的密码
 * @author qsyyke
 */

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier(value = "userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.queryUserByUsername(username);

        if (user == null) {
            //用户不存在
            throw new UsernameNotFoundException(username + "用户不存在");
        }

        List<String> grantedAuthorities = new ArrayList<>();

        //将从数据库中查询出来的role和权限增加到grantedAuthorities集合中
        grantedAuthorities.add(user.getUserPermission().getRole());
        grantedAuthorities.addAll(Arrays.asList(user.getUserPermission().getPermission().split(",")));


        //用户存在，封装UserDetails
        CustomUserDetails userDetails = new CustomUserDetails(
                username,user.getPassword(),true,true,
                user.getUserPermission().isAccountNonLocked(),
                user.getUserPermission().isAccountNonExpired(),null);
        //将权限集合放入userDetails对象中
        userDetails.setGrantedAuthorities(grantedAuthorities);
        return userDetails;
    }
}
