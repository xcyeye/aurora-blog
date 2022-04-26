package xyz.xcye.common.vo;

import lombok.Data;
import xyz.xcye.common.entity.table.LoginInfoDO;
import xyz.xcye.common.entity.table.NavigationDO;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserVO {

    /**
     * 用户唯一id
     */
    private Long uid;

    /**
     * 用户简介
     */
    private String userSummary;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户多少天免登录
     */
    private Integer rememberMeDay;

    /**
     * 该用户对应的网站设置的uid
     */
    private Long siteUid;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户级别 0：管理员 1：普通用户
     */
    private Integer userLevel;

    /**
     * 用户名（和用户昵称不同，用户名唯一，昵称只要符合要求都行）
     */
    private String username;

    /**
     * 专业
     */
    private String profession;

    /**
     * 用户的邮箱是否验证过 true：已验证 false：未验证
     */
    private Boolean verifyEmail;

    /**
     * 该用户对应的邮箱设置uid
     */
    private Long emailUid;

    /**
     * 角色 角色的命名必须遵循spring security规范，以ROLE_XXX，一个用户只能有一个角色
     */
    private String role;

    /**
     * 用户权限集合，可以有多个，使用,分割开
     */
    private String permission;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     *
     */
    private NavigationDO navigationDO;

    private LoginInfoDO loginInfoDO;
}