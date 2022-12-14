package xyz.xcye.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;

/**
 * @description role_permission数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
 * @author xcye <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionRelationshipPojo {

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

}