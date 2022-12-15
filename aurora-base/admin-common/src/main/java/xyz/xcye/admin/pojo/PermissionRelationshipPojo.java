package xyz.xcye.admin.pojo;

import lombok.Data;

/**
 * @author xcye
 * @description
 * @date 2022-12-15 08:15:40
 */

@Data
public class PermissionRelationshipPojo {

    private Long[] userUidArr;
    private Long[] roleUidArr;
    private Long[] permissionUidArr;
    private Long originRoleUid;
    private Long newRoleUid;
    private Long originPermissionUid;
    private Long newPermissionUid;
}
