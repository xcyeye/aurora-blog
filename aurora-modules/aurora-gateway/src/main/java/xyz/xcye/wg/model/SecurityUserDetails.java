package xyz.xcye.wg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author qsyyke
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUserDetails implements UserDetails {

    /**
     * 用户的uid
     */
    private Long userUid;

    /**
     * 是否验证了邮箱
     */
    private Boolean verifyEmail;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 该用户是否激活
     */
    private boolean enable;

    /**
     * 密码是否过期
     */
    private boolean credentialsNonExpired;

    /**
     * 账户是否被锁定
     */
    private boolean accountNonLocked;

    /**
     * 账户是否过期
     */
    private boolean accountNonExpired;

    /**
     * 用户所拥有的角色和权限，角色必须是ROLE_xxx，并且只有一个
     */
    private List<GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    /**
     * 设置用户所拥有的权限信息
     * @param permissionInfo 权限和角色集合
     */
    /*public void setGrantedAuthorities(List<String> permissionInfo) {
        grantedAuthorities = new ArrayList<>();
        permissionInfo.forEach(permission -> {
            grantedAuthorities.add((GrantedAuthority) () -> permission);
        });
    }*/
}