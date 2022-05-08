package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@RequestMapping("/admin/role")
@RestController
@Api(tags = "角色相关的操作")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ModifyOperation
    @ApiOperation(value = "插入角色")
    @PostMapping("")
    public int insertRole(@Validated({Insert.class, Default.class}) Role role) {
        return roleService.insertRole(role);
    }

    @ModifyOperation
    @ApiOperation(value = "修改角色信息")
    @PutMapping("")
    public int updateRole(@Validated({Update.class, Default.class}) Role role) {
        return roleService.updateRole(role);
    }

    @ModifyOperation
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{uid}")
    public int deleteRole(@PathVariable("uid") int uid) {
        return roleService.deleteByUid(uid);
    }

    @SelectOperation
    @ApiOperation(value = "根据uid查询角色")
    @GetMapping("/{uid}")
    public Role queryRoleByUid(@PathVariable("uid") int uid) {
        return roleService.selectByUid(uid);
    }

    @SelectOperation
    @ApiOperation(value = "查询满足要求的所有角色信息")
    @GetMapping("")
    public PageData<Role> queryRoleByUid(Condition<Long> condition) {
        return roleService.selectAllRole(condition);
    }
}
