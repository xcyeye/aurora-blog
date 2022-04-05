package xyz.xcye.wg.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * 设置用户所拥有的权限信息
     * @param permissionInfo 权限和角色集合
     */
    public void setGrantedAuthorities(List<String> permissionInfo) {
        grantedAuthorities = new ArrayList<>();
        permissionInfo.forEach(permission -> {
            grantedAuthorities.add((GrantedAuthority) () -> permission);
        });
    }

    public CustomUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
