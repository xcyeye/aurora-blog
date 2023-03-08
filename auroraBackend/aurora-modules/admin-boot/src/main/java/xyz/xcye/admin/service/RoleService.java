package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.pojo.RolePojo;
import xyz.xcye.admin.vo.RoleVO;
import xyz.xcye.core.constant.RedisConstant;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.role.RoleException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;
import xyz.xcye.service.redis.annotation.CleanRedisData;
import xyz.xcye.service.redis.annotation.GetByRedis;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:04
 */

@Service
public class RoleService {

    @Autowired
    private AuroraRoleService auroraRoleService;

    @Transactional
    @CleanRedisData
    public void insertRole(RolePojo role) {
        Assert.notNull(role, "角色信息不能为null");
        AssertUtils.ifNoLengthThrow(role.getName(), () -> new RoleException("角色名字不能为null"));
        // 查看此角色是否存在
        AssertUtils.stateThrow(queryListRoleByCondition(Condition.instant(role.getName())).getResult().isEmpty(),
                () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        role.setStatus(true);
        auroraRoleService.insert(BeanUtils.copyProperties(role, Role.class));
    }

    @CleanRedisData(otherKey = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int updateRole(RolePojo role) {
        // 判断角色是否存在
        Role queryRole = auroraRoleService.queryById(role.getUid());
        if (queryRole != null && StringUtils.hasLength(role.getName()) && !queryRole.getName().equals(role.getName())) {
            Role one = auroraRoleService.queryOne(new Role() {{
                setName(role.getName());
            }});
            AssertUtils.stateThrow(one == null, () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_EXISTS));
        }
        return auroraRoleService.updateById(BeanUtils.copyProperties(role, Role.class));
    }

    @CleanRedisData(otherKey = RedisConstant.STORAGE_ROLE_PERMISSION_INFO)
    public int physicalDeleteRole(long uid) {
        return auroraRoleService.deleteById(uid);
    }

    @GetByRedis
    public PageData<RoleVO> queryListRoleByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(auroraRoleService.queryListByCondition(condition), RoleVO.class);
    }

    @GetByRedis
    public RoleVO queryRoleByUid(long uid) {
        return BeanUtils.copyProperties(auroraRoleService.queryById(uid), RoleVO.class);
    }

    @GetByRedis
    public RoleVO queryOneRole(RolePojo pojo) {
        return BeanUtils.copyProperties(auroraRoleService.queryOne(BeanUtils.copyProperties(pojo, Role.class)), RoleVO.class);
    }
}
