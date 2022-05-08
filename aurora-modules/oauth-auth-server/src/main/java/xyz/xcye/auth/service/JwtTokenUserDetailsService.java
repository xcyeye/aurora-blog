package xyz.xcye.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.po.User;
import xyz.xcye.auth.model.SecurityUserDetails;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.oauth.api.service.RolePermissionFeignService;
import xyz.xcye.oauth.api.service.UserFeignService;

import java.util.*;

/**
 * 在此loadUserByUsername()方法中，用户用户名，远程调用admin服务，查询出用户的信息
 * @author qsyyke
 * @date Created in 2022/5/4 14:17
 */


@Service
public class JwtTokenUserDetailsService implements UserDetailsService {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private RolePermissionFeignService rolePermissionFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasLength(username)) {
            throw new UsernameNotFoundException("用户名不能为空或者null");
        }

        User user = null;
        // 根据用户名远程查询用户信息
        try {
            String json = ConvertObjectUtils.jsonToString(userFeignService.queryUserByUsernameContainPassword(username));
            user = JSONUtils.parseObjFromResult(json, "data", User.class);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username + "不存在");
        }

        // 用户存在，获取用户的角色信息
        List<Map<String,String>> rolePermissionSet = null;
        try {
            rolePermissionSet = (List<Map<String, String>>) rolePermissionFeignService.loadPermissionByUsername(username).getData();
        } catch (Exception e) {
            throw new UsernameNotFoundException("获取" + username + "用户的权限信息失败");
        }

        // 将该用户所拥有的角色放入集合中
        Set<String> roleSet = new HashSet<>();
        rolePermissionSet.forEach(rolePermissionMap -> {
            rolePermissionMap.entrySet().forEach(element -> {
                String roleName = element.getKey();
                String permissionPath = element.getValue();
                roleSet.add(roleName);
            });
        });

        return SecurityUserDetails.builder()
                .username(username)
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .gender(user.getGender())
                .verifyEmail(user.getVerifyEmail())
                .password(user.getPassword())
                .userUid(user.getUid())
                .accountNonLocked(!Optional.ofNullable(user.getAccountLock()).orElse(false))
                .grantedAuthorities(AuthorityUtils.createAuthorityList(String.join(",", roleSet).split(","))).build();
    }
}
