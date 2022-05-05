package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.service.PermissionRelationService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.annotaion.controller.SelectOperation;

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
    @GetMapping("/userUid")
    public Set<Map<String,String>> loadPermissionByUserUid(long userUid) {
        return permissionRelationService.loadPermissionByUserUid(userUid);
    }

    @SelectOperation
    @GetMapping("根据角色名称，加载对应的角色-权限信息，map集合形式为[ROLE_admin,GET:http://aurora.xcye.xyz]")
    @ApiOperation(value = "")
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
}
