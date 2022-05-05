  package xyz.xcye.admin.controller;

  import io.swagger.annotations.Api;
  import io.swagger.annotations.ApiOperation;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.validation.annotation.Validated;
  import org.springframework.web.bind.annotation.*;
  import xyz.xcye.admin.po.Permission;
  import xyz.xcye.admin.service.PermissionService;
  import xyz.xcye.core.annotaion.controller.ModifyOperation;
  import xyz.xcye.core.annotaion.controller.SelectOperation;
  import xyz.xcye.core.valid.Insert;
  import xyz.xcye.core.valid.Update;
  import xyz.xcye.mybatis.entity.Condition;
  import xyz.xcye.mybatis.entity.PageData;

  import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@RequestMapping("/admin/permission")
@RestController
@Api(tags = "权限相关的操作")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ModifyOperation
    @ApiOperation(value = "插入角色")
    @PostMapping("")
    public int insertPermission(@Validated({Insert.class, Default.class}) Permission permission) {
        return permissionService.insertPermission(permission);
    }

    @ModifyOperation
    @ApiOperation(value = "修改角色信息")
    @PutMapping("")
    public int updatePermission(@Validated({Update.class, Default.class}) Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @ModifyOperation
    @ApiOperation(value = "删除权限")
    @DeleteMapping("/{uid}")
    public int deletePermission(@PathVariable("uid") int uid) {
        return permissionService.deleteByUid(uid);
    }

    @SelectOperation
    @ApiOperation(value = "根据uid查询权限")
    @GetMapping("/{uid}")
    public Permission queryPermissionByUid(@PathVariable("uid") int uid) {
        return permissionService.selectByUid(uid);
    }

    @SelectOperation
    @ApiOperation(value = "查询满足要求的所有权限信息")
    @GetMapping("")
    public PageData<Permission> queryPermissionServiceByUid(Condition<Long> condition) {
        return permissionService.selectAllPermission(condition);
    }
}
