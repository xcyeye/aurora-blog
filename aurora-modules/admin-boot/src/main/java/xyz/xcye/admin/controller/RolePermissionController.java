package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.service.PermissionRelationService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.data.entity.Condition;

import java.util.Map;
import java.util.Set;

/**
 * 角色权限信息控制器
 * @author qsyyke
 * @date Created in 2022/5/4 22:43
 */

@Api(tags = "角色权限信息控制器")
@RestController
@RequestMapping("/admin/permissionRelation")
public class RolePermissionController {

    @Autowired
    private PermissionRelationService permissionRelationService;

    @SelectOperation
    @ApiOperation(value = "根据用户uid，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]")
    @GetMapping("/userUid/{userUid}")
    public Set<Map<String,String>> loadPermissionByUserUid(@PathVariable("userUid") long userUid) {
        return permissionRelationService.loadPermissionByUserUid(userUid);
    }

    @SelectOperation
    @GetMapping("/rolePermission")
    @ApiOperation("加载角色权限关系信息")
    public Set<Map<String, RolePermissionDTO>> loadAllRolePermission(Condition<Long> condition) {
        return permissionRelationService.loadAllRolePermission(condition);
    }

    @SelectOperation
    @ApiOperation(value = "根据用户uid，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]")
    @GetMapping("/username/{username}")
    public Set<Map<String,String>> loadPermissionByUsername(@PathVariable("username") String username) {
        return permissionRelationService.loadPermissionByUsername(username);
    }

    @SelectOperation
    @GetMapping("/roleName")
    @ApiOperation(value = "根据角色名称，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]")
    public Set<Map<String,String>> loadPermissionByRoleName(@RequestParam("roleName") String roleName) {
        return permissionRelationService.loadPermissionByRoleName(roleName);
    }

    @SelectOperation
    @GetMapping("/role")
    @ApiOperation(value = "根据permissionPath，查询哪些角色可以访问")
    public Set<Role> queryRoleByPermissionPath(@RequestParam("permissionPath") String permissionPath) {
        return permissionRelationService.queryRoleByPermissionPath(permissionPath);
    }


    @SelectOperation
    @ApiOperation("根据此permissionPath，查询哪些用户可以访问")
    @GetMapping("/user")
    public Set<UserVO> queryUserByPermissionPath(@RequestParam("permissionPath") String permissionPath) {
        return permissionRelationService.queryUserByPermissionPath(permissionPath);
    }


    @ModifyOperation
    @ApiOperation(value = "批量为多个用户增加角色")
    @PostMapping("/insertUserRoleBatch")
    public R insertUserRoleBatch(long[] userUid, long roleUid) {
        return R.success(ResponseStatusCodeEnum.SUCCESS.getCode(),
                ResponseStatusCodeEnum.SUCCESS.getMessage(),
                "为" + permissionRelationService.insertUserRoleBatch(userUid, roleUid) + "个用户增加了角色", true);
    }

    @ModifyOperation
    @ApiOperation(value = "批量为多个角色增加权限")
    @PostMapping("/insertRolePermissionBatch")
    public R insertRolePermissionBatch(long[] roleUid, long permissionUid) {
        return R.success(ResponseStatusCodeEnum.SUCCESS.getCode(),
                ResponseStatusCodeEnum.SUCCESS.getMessage(),
                "添加成功数" + permissionRelationService.insertRolePermissionBatch(roleUid, permissionUid), true);
    }

    @ModifyOperation
    @ApiOperation(value = "为某个用户删除多个角色")
    @DeleteMapping("/deleteUserRoleBatch")
    public int deleteUserRoleBatch(long userUid, long[] roleUid) {
        return permissionRelationService.deleteUserRoleBatch(userUid, roleUid);
    }

    @ModifyOperation
    @ApiOperation(value = "修改某个用户的角色")
    @PutMapping("/updateUserRole")
    public int updateUserRole(long userUid, long originRoleUid, long newRoleUid) {
        return permissionRelationService.updateUserRole(userUid, originRoleUid, newRoleUid);
    }

    @ModifyOperation
    @ApiOperation(value = "删除某个角色的多个权限")
    @DeleteMapping("/deleteRolePermissionBatch")
    public int deleteRolePermissionBatch(long roleUid, long[] permissionUid) {
        return permissionRelationService.deleteRolePermissionBatch(roleUid, permissionUid);
    }

    @ModifyOperation
    @ApiOperation(value = "更新某个角色的权限")
    @PutMapping("/updateRolePermission")
    public int updateRolePermission(long roleUid, long originPermissionUid, long newPermissionUid) {
        return permissionRelationService.updateRolePermission(roleUid, originPermissionUid, newPermissionUid);
    }
}
