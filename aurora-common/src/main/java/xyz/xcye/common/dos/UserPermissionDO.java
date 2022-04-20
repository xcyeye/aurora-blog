package xyz.xcye.common.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.validator.ValidateString;

/**
 * 数据表 au_user_permission
 * @author qsyyke
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPermissionDO {

    /**
     * 唯一uid
     */
    private Long uid;

    /**
     * 用户角色，必须满足ROLE_xxx
     */
    @ValidateString(value = "用户角色",max = FieldLengthConstant.USER_ROLE)
    private String role;

    /**
     * 用户权限 多个权限之间，使用,分隔开
     */
    @ValidateString(value = "用户权限",max = FieldLengthConstant.USER_PERMISSION)
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
