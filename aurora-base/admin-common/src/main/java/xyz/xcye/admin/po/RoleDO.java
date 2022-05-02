package xyz.xcye.admin.po;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * @author qsyyke
 */

@Builder
@Data
public class RoleDO {
    /**
     * 唯一uid，不需要雪花生成
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Integer uid;

    /**
     * 角色或者权限
     */
    @ValidateString(value = "权限或者角色",max = FieldLengthConstant.USER_PERMISSION)
    private String role;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;
}
