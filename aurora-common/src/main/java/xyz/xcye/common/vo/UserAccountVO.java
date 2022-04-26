package xyz.xcye.common.vo;

import lombok.Data;

@Data
public class UserAccountVO {

    /**
     * 唯一uid
     */
    private Long uid;

    /**
     * 用户角色，必须满足ROLE_xxx
     */
    private String role;

    /**
     * 用户权限 多个权限之间，使用,分隔开
     */
    private String permission;

    /**
     * 账户没有被锁住
     * <p>true被锁住，反之</p>
     */
    private Boolean accountLocked;

    /**
     * 账户没有过期
     * <p>true表示过期</p>
     */
    private Boolean accountExpired;

    /**
     * 从用户权限是哪个用户拥有的
     */
    private Long userUid;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}