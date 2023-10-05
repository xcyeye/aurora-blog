package xyz.xcye.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xcye <br/>
 * @description role_permission数据表的POJO <br/>
 * @date 2022-12-13 20:15:14 <br/>
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
    private List<Long> roleUidArr;

    @NotNull(groups = {Insert.class})
    private List<Long> userUidArr;
    /**
     *
     */
    @NotNull(groups = {Insert.class})
    private List<Long> permissionUidArr;

    private List<Long> originRoleUidArr;
    private List<Long> newRoleUidArr;
    private List<Long> originPermissionUidArr;
    private List<Long> newPermissionUidArr;

    private List<String> usernameArr;
    private List<String> roleNameArr;
    private List<String> permissionPathArr;

}