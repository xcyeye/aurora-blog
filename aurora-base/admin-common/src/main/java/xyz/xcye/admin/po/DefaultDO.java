package xyz.xcye.admin.po;

import lombok.Data;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 默认值
 * @author qsyyke
 */

@Data
public class DefaultDO {
    /**
     * 唯一uid
     */
    private Long uid;

    /**
     * 免登录天数
     */
    @NotNull(groups = Insert.class)
    private Integer rememberMeDay;

    /**
     * 角色
     */
    @ValidateString(value = "用户的角色",max = FieldLengthConstant.USER_ROLE)
    private String role;

    /**
     * 权限
     */
    @ValidateString(value = "用户的权限",max = FieldLengthConstant.USER_PERMISSION)
    private String permission;
}
