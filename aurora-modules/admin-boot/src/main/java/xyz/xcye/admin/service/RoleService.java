package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.pojo.RolePojo;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.role.RoleException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:04
 */

@Service
public class RoleService {

    @Autowired
    private AuroraRoleService auroraRoleService;

    @Transactional
    public void insertRole(RolePojo role) {
        Assert.notNull(role, "角色信息不能为null");
        AssertUtils.ifNoLengthThrow(role.getName(), () -> new RoleException("角色名字不能为null"));
        // 查看此角色是否存在
        AssertUtils.stateThrow(selectAllRole(Condition.instant(role.getName())).getResult().isEmpty(),
                () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        role.setStatus(false);
        auroraRoleService.insert(BeanUtils.copyProperties(role, Role.class));
    }

    public int updateRoleStatus(long uid, boolean status) {
        RolePojo rolePojo = new RolePojo();
        rolePojo.setUid(uid);
        rolePojo.setStatus(status);
        return updateRole(rolePojo);
    }

    public int updateRole(RolePojo role) {
        // 判断角色是否存在
        AssertUtils.stateThrow(selectAllRole(Condition.instant(role.getName())).getResult().isEmpty(),
                () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        return auroraRoleService.updateById(BeanUtils.copyProperties(role, Role.class));
    }

    public int deleteByUid(long uid) {
        return auroraRoleService.deleteById(uid);
    }

    public PageData<Role> selectAllRole(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> auroraRoleService.queryListByCondition(condition));
    }

    public Role selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(auroraRoleService.queryListByCondition(Condition.instant(uid, true)), Role.class);
    }
}
