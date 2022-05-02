package xyz.xcye.admin.po;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 数据表 au_user_permission
 * @author qsyyke
 */

@Builder
@Data
public class UserAccountDO implements Serializable {

    public static final long serialVersionUID = 42L;

    /**
     * 唯一uid
     */
    @NotNull(groups = {Update.class})
    private Long uid;

    /**
     * 用户角色，必须满足ROLE_xxx
     */
    @Pattern(regexp = "^(ROLE_)[a-zA-Z]{1,10}",message = "角色命名必须ROLE_XXX，并且总长度不能大于15")
    private String role;

    /**
     * 是否删除
     */
    private Boolean delete;

    /**
     * 用户权限 多个权限之间，使用,分隔开
     */
    @Length(max = FieldLengthConstant.USER_PERMISSION)
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
