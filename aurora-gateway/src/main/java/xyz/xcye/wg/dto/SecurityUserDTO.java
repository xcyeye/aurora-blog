package xyz.xcye.wg.dto;

import lombok.Data;
import xyz.xcye.common.dos.UserAccountDO;

/**
 * @author qsyyke
 */

@Data
public class SecurityUserDTO {
    /**
     * 用户唯一id
     */
    private Long uid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户多少天免登录
     */
    private Integer noLoginDay;

    /**
     * 用户的删除状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private Integer userLevel;

    /**
     * 密码，使用md5加密
     */
    private String password;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    private String username;

    /**
     * 用户的邮箱是否验证过 true：已验证 false：未验证
     */
    private Boolean verifyEmail;

    /**
     * 角色 角色的命名必须遵循spring security规范，以ROLE_XXX，一个用户只能有一个角色
     */
    private String role;

    /**
     * 用户权限集合，可以有多个，使用,分割开
     */
    private String permission;

    private UserAccountDO userAccountDO;
}
