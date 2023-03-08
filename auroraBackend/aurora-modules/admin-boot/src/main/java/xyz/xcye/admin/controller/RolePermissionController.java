package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.pojo.RolePermissionRelationshipPojo;
import xyz.xcye.admin.service.PermissionRelationService;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.data.entity.Condition;

import java.util.List;

/**
 * 角色权限信息控制器
 * @author qsyyke
 * @date Created in 2022/5/4 22:43
 */

@Tag(name = "角色权限信息控制器")
@RestController
@RequestMapping("/admin/permissionRelation")
public class RolePermissionController {

    @Autowired
    private PermissionRelationService permissionRelationService;

    @SelectOperation
    @Operation(summary = "根据用户uid，加载该用户所拥有的角色权限关系")
    @PostMapping("/loadPermissionByUserUid")
    public List<RolePermissionDTO> loadPermissionByUserUid(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.loadPermissionByUserUid(pojo);
    }

    @SelectOperation
    @PostMapping("/loadAllRolePermission")
    @Operation(summary = "加载所有的角色权限关系，只返回该角色存在权限部分，如果某个角色没有赋予权限，则不返回")
    public List<RolePermissionDTO> loadAllRolePermission(@RequestBody Condition<Long> condition) {
        return permissionRelationService.loadAllRolePermission(condition);
    }

    @SelectOperation
    @PostMapping("/loadRolePermissionRelByRoleUid")
    @Operation(summary = "加载角色权限关系根据角色名，不返回用户信息")
    public List<RolePermissionDTO> loadRolePermissionRelByRoleUid(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.loadRolePermissionRelByRoleUid(pojo);
    }

    @SelectOperation
    @PostMapping("/loadAllRoleByUsername")
    @Operation(summary = "根据用户名，获取该用户所拥有的所有角色")
    public List<Role> loadAllRoleByUsername(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.loadAllRoleByUsername(pojo);
    }

    @SelectOperation
    @Operation(summary = "根据用户名，加载该用户所拥有的角色权限关系，此接口和loadPermissionByUserUid返回的数据一样")
    @PostMapping("/loadPermissionByUsername")
    public List<RolePermissionDTO> loadPermissionByUsername(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.loadPermissionByUsername(pojo);
    }

    @SelectOperation
    @PostMapping("/loadPermissionByRoleName")
    @Operation(summary = "根据角色名称，加载对应的角色-权限信息")
    public List<RolePermissionDTO> loadPermissionByRoleName(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.loadPermissionByRoleName(pojo);
    }

    @SelectOperation
    @PostMapping("/queryRoleByPermissionPath")
    @Operation(summary = "根据permissionPath，查询哪些角色和角色可以可以访问")
    public List<RolePermissionDTO> queryRoleByPermissionPath(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.queryRoleByPermissionPath(pojo);
    }

    @ModifyOperation
    @Operation(summary = "批量为多个用户增加角色")
    @PostMapping("/batchInsertUserRole")
    public void batchInsertUserRole(@RequestBody RolePermissionRelationshipPojo pojo) {
        permissionRelationService.insertUserRoleBatch(pojo);
    }

    @ModifyOperation
    @Operation(summary = "批量为多个角色增加权限")
    @PostMapping("/batchInsertRolePermission")
    public void batchInsertRolePermission(@RequestBody RolePermissionRelationshipPojo pojo) {
        permissionRelationService.insertRolePermissionBatch(pojo);
    }

    @ModifyOperation
    @Operation(summary = "为某个用户删除多个角色")
    @PostMapping("/batchDeleteUserRole")
    public int batchDeleteUserRole(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.deleteUserRoleBatch(pojo);
    }

    @ModifyOperation
    @Operation(summary = "修改某个用户的角色")
    @PostMapping("/updateUserRole")
    public int updateUserRole(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.updateUserRole(pojo);
    }

    @ModifyOperation
    @Operation(summary = "删除某个角色的多个权限")
    @PostMapping("/batchDeleteRolePermission")
    public int batchDeleteRolePermission(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.deleteRolePermissionBatch(pojo);
    }

    @ModifyOperation
    @Operation(summary = "更新某个角色的权限")
    @PostMapping("/updateRolePermission")
    public int updateRolePermission(@RequestBody RolePermissionRelationshipPojo pojo) {
        return permissionRelationService.updateRolePermission(pojo);
    }
}
