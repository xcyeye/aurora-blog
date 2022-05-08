package xyz.xcye.admin.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色到用户的关系信息
 * @TableName au_role_permission
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionRelationship implements Serializable {
    /**
     * 唯一uid，如果记录不多的话，可以直接使用int类型
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色的uid
     */
    @NotNull(groups = {Insert.class})
    private Long roleUid;

    /**
     *
     */
    @NotNull(groups = {Insert.class})
    private Long permissionUid;

    private static final long serialVersionUID = 1L;
}