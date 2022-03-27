package xyz.xcye.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.entity.R;
import xyz.xcye.entity.table.Permission;
import xyz.xcye.enums.ResultCode;
import xyz.xcye.service.PermissionService;

/**
 * @author qsyyke
 */

@Slf4j
@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/insert")
    public R insertPermission(@Validated Permission permission) {
        log.info(String.valueOf(permission));
        Permission insertPermission = permissionService.insertPermission(permission);

        R success = R.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), insertPermission);
        System.out.println(success);
        return success;
    }
}
