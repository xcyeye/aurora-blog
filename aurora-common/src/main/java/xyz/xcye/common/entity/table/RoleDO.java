package xyz.xcye.common.entity.table;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

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
