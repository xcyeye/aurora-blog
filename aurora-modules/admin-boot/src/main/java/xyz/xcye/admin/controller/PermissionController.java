  package xyz.xcye.admin.controller;

  import io.swagger.v3.oas.annotations.Operation;
  import io.swagger.v3.oas.annotations.tags.Tag;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.validation.annotation.Validated;
  import org.springframework.web.bind.annotation.*;
  import xyz.xcye.admin.po.Permission;
  import xyz.xcye.admin.pojo.PermissionPojo;
  import xyz.xcye.admin.service.PermissionService;
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

@RequestMapping("/admin/permission")
@RestController
@Tag(name = "权限相关的操作")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ModifyOperation
    @Operation(summary = "插入路径权限")
    @PostMapping("")
    public void insertPermission(@Validated({Insert.class, Default.class}) PermissionPojo permission) {
        permissionService.insertPermission(permission);
    }

    @ModifyOperation
    @Operation(summary = "修改路径权限信息")
    @PutMapping("")
    public int updatePermission(@Validated({Update.class, Default.class}) PermissionPojo permission) {
        return permissionService.updatePermission(permission);
    }

    @ModifyOperation
    @Operation(summary = "删除权限")
    @DeleteMapping("/{uid}")
    public int deletePermission(@PathVariable("uid") int uid) {
        return permissionService.deleteByUid(uid);
    }

    @SelectOperation
    @Operation(summary = "根据uid查询权限")
    @GetMapping("/{uid}")
    public Permission queryPermissionByUid(@PathVariable("uid") int uid) {
        return permissionService.selectByUid(uid);
    }

    @SelectOperation
    @Operation(summary = "查询满足要求的所有权限信息")
    @GetMapping("")
    public PageData<Permission> queryPermissionServiceByUid(Condition<Long> condition) {
        return permissionService.selectAllPermission(condition);
    }
}
