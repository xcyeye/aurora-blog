package xyz.xcye.entity.table;

import lombok.Data;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.validator.ValidateString;

import java.math.BigInteger;

/**
 * 数据表 au_user_permission
 * @author qsyyke
 */

@Data
public class UserPermission {

    /**
     * 唯一uid
     */
    private BigInteger uid;

    /**
     * 用户角色，必须满足ROLE_xxx
     */
    @ValidateString(value = "用户角色",max = FieldLengthEnum.USER_ROLE)
    private String role;

    /**
     * 用户权限
     */
    @ValidateString(value = "用户权限",max = FieldLengthEnum.USER_PERMISSION)
    private String permission;

    /**
     * 账户没有被锁住
     * <p>true表示没有被锁住，反之</p>
     */
    private boolean accountNonLocked;

    /**
     * 账户没有过期
     * <p>true表示没有过期</p>
     */
    private boolean accountNonExpired;

    /**
     * 从用户权限是哪个用户拥有的
     */
    private BigInteger userUid;
}
