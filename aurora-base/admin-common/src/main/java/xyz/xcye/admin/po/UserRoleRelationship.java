package xyz.xcye.admin.po;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户到角色的关系信息
 * @TableName au_user_role
 */
@Data
@Builder
public class UserRoleRelationship implements Serializable {
    /**
     * 
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 角色uid
     */
    @NotNull(groups = {Insert.class})
    private Long roleUid;

    /**
     * 用户uid
     */
    @NotNull(groups = {Insert.class})
    private Long userUid;

    private static final long serialVersionUID = 1L;
}