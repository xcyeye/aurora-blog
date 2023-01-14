package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.dto.AdminRouterDTO;
import xyz.xcye.admin.pojo.RouterPermissionPojo;
import xyz.xcye.admin.service.PermissionAdminRouterRelationshipService;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author qsyyke
 */

@RequestMapping("/admin/routerPermission")
@RestController
@Tag(name = "路由权限关系")
public class RouterPermissionController {

    @Autowired
    private PermissionAdminRouterRelationshipService permissionAdminRouterRelationshipService;

    @ModifyOperation
    @Operation(summary = "新建一条路由权限关系")
    @PostMapping("/insertRouterPermission")
    public void insertRouterPermission(@Validated({Insert.class, Default.class}) @RequestBody RouterPermissionPojo pojo) {
        permissionAdminRouterRelationshipService.insertRouterPermission(pojo);
    }

    @ModifyOperation
    @Operation(summary = "修改路由权限信息")
    @PostMapping("/updateRouterPermission")
    public int updateRouterPermission(@Validated({Update.class, Default.class}) @RequestBody RouterPermissionPojo pojo) {
        return permissionAdminRouterRelationshipService.updateRouterPermission(pojo);
    }

    @ModifyOperation
    @Operation(summary = "删除路由权限信息")
    @PostMapping("/physicalDeleteRouterPermission")
    public int physicalDeleteRouterPermission(@RequestBody RouterPermissionPojo pojo) {
        return permissionAdminRouterRelationshipService.physicalDeleteRouterPermission(pojo);
    }

    @SelectOperation
    @Operation(summary = "根据权限uid查询此权限拥有的路由")
    @PostMapping(value = "/queryAllAdminRouterInfoByPermissionUid", produces = "application/json;charset=UTF-8")
    public List<AdminRouterDTO> queryAllAdminRouterInfoByPermissionUid(@RequestBody RouterPermissionPojo pojo) {
        return permissionAdminRouterRelationshipService.queryAllAdminRouterInfoByPermissionUid(pojo);
    }
}
