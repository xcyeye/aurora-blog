package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.dao.PermissionMapper;
import xyz.xcye.admin.po.Permission;
import xyz.xcye.admin.service.PermissionService;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.permission.PermissionException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:34
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int deleteByUid(long uid) {
        return permissionMapper.deleteByUid(uid);
    }

    @Override
    public int insertPermission(Permission permission) {
        Objects.requireNonNull(permission,"方法路径信息不能为null");
        permission.setCreateTime(DateUtils.format());
        // 判断path是否符合规范，必须是GET:Path这种形式 不支持中文路径

        AssertUtils.stateThrow(matchesResourcePath(permission.getPath()), () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        return permissionMapper.insertPermission(permission);
    }

    @Override
    public int updatePermission(Permission permission) {
        Objects.requireNonNull(permission, "资源路径权限信息不能为null");
        permission.setUpdateTime(DateUtils.format());
        if (StringUtils.hasLength(permission.getPath())) {
            AssertUtils.stateThrow(matchesResourcePath(permission.getPath()),
                    () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        }else {
            // 没有path
            permission.setPath(null);
        }
        return permissionMapper.updatePermission(permission);
    }

    @Override
    public PageData<Permission> selectAllPermission(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> permissionMapper.queryAllPermission(condition));
    }

    private boolean matchesResourcePath(String resourcePath) {
        return Pattern.matches("^(GET|DELETE|POST|PUT):[a-zA-Z/-_.]+",resourcePath);
    }

    @Override
    public Permission selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(permissionMapper.queryAllPermission(Condition.instant(uid, true, null, null)), Permission.class);
    }
}
