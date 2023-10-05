package xyz.xcye.oauth.api.service.handler;

import org.springframework.stereotype.Component;
import xyz.xcye.admin.pojo.RolePermissionRelationshipPojo;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.oauth.api.service.RolePermissionFeignService;

/**
 * 角色权限信息控制器
 *
 * @author qsyyke
 * @date Created in 2022/5/4 22:43
 */

@Component
public class RolePermissionFeignHandler implements RolePermissionFeignService {


    @Override
    public R loadPermissionByUsername(RolePermissionRelationshipPojo pojo) {
        return R.failure();
    }

    @Override
    public R loadAllRoleByUsername(RolePermissionRelationshipPojo pojo) {
        return R.failure();
    }

    @Override
    public R loadAllRolePermission(Condition<Long> condition) {
        return R.failure();
    }
}
