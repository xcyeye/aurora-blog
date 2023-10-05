package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.pojo.RolePojo;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.admin.vo.RoleVO;
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
@Tag(name = "角色相关的操作")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ModifyOperation
    @Operation(summary = "插入角色")
    @PostMapping("/insertRole")
    public void insertRole(@Validated({Insert.class, Default.class}) @RequestBody RolePojo role) {
        roleService.insertRole(role);
    }

    @ModifyOperation
    @Operation(summary = "修改角色信息")
    @PostMapping("/updateRole")
    public int updateRole(@Validated({Update.class, Default.class}) @RequestBody RolePojo role) {
        return roleService.updateRole(role);
    }

    @ModifyOperation
    @Operation(summary = "删除角色")
    @PostMapping("/physicalDeleteRole")
    public int physicalDeleteRole(@RequestBody RolePojo role) {
        return roleService.physicalDeleteRole(role.getUid());
    }

    @SelectOperation
    @Operation(summary = "根据uid查询角色")
    @PostMapping(value = "/queryRoleByUid", produces = "application/json;charset=UTF-8")
    public RoleVO queryRoleByUid(@RequestBody RolePojo role) {
        return roleService.queryRoleByUid(role.getUid());
    }

    @SelectOperation
    @Operation(summary = "查询满足要求的所有角色信息")
    @PostMapping("/queryListRoleByCondition")
    public PageData<RoleVO> queryListRoleByCondition(@RequestBody Condition<Long> condition) {
        return roleService.queryListRoleByCondition(condition);
    }
}
