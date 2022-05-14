package xyz.xcye.auth.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.po.User;
import xyz.xcye.auth.model.SecurityUserDetails;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.oauth.api.service.RolePermissionFeignService;
import xyz.xcye.oauth.api.service.UserFeignService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            R r = userFeignService.queryUserByUsernameContainPassword(username);
            String json = ConvertObjectUtils.jsonToString(r);
            user = JSONUtils.parseObjFromResult(json, "data", User.class);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
            throw new UsernameNotFoundException(username + "不存在");
        }

        if (user != null && user.getUid() == null) {
            throw new UsernameNotFoundException(username + "不存在");
        }

        // 用户存在，获取用户的角色信息
        List<JSONObject> rolePermissionDTOList = null;
        try {
            R r = rolePermissionFeignService.loadAllRoleByUsername(username);
            rolePermissionDTOList = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", List.class);
        } catch (Exception e) {
            LogUtils.logExceptionInfo(e);
            throw new UsernameNotFoundException("获取" + username + "用户的权限信息失败");
        }

        // 将该用户所拥有的角色放入集合中
        String[] roleArray = rolePermissionDTOList.stream()
                .map(jsonObj -> (String) jsonObj.get("name"))
                .distinct()
                .collect(Collectors.joining(","))
                .split(",");

        return SecurityUserDetails.builder()
                .username(username)
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .gender(user.getGender())
                .verifyEmail(user.getVerifyEmail())
                .password(user.getPassword())
                .userUid(user.getUid())
                .accountNonLocked(!Optional.ofNullable(user.getAccountLock()).orElse(false))
                .grantedAuthorities(AuthorityUtils.createAuthorityList(roleArray)).build();
    }
}
