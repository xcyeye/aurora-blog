package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.xcye.admin.dao.RoleMapper;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.core.exception.role.RoleException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:04
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int insertRole(Role role) {
        Assert.notNull(role, "角色信息不能为null");
        AssertUtils.ifNullThrow(role.getName(), () -> new RoleException("角色名字不能为null"));
        role.setCreateTime(DateUtils.format());
        role.setStatus(false);
        return roleMapper.insertRole(role);
    }

    @Override
    public int updateRoleStatus(long uid, boolean status) {
        Role role = Role.builder()
                .uid(uid).status(status)
                .build();
        return updateRole(role);
    }

    @Override
    public int updateRole(Role role) {
        role.setUpdateTime(DateUtils.format());
        return roleMapper.updateRole(role);
    }

    @Override
    public int deleteByUid(long uid) {
        return roleMapper.deleteByUid(uid);
    }

    @Override
    public PageData<Role> selectAllRole(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> roleMapper.selectAllRole(condition));
    }

    @Override
    public Role selectByUid(long uid) {
        return BeanUtils.getSingleObjFromList(roleMapper.selectAllRole(Condition.instant(uid, true)), Role.class);
    }
}
