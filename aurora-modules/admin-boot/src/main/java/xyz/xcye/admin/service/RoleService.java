package xyz.xcye.admin.service;


import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.RoleDO;
import xyz.xcye.common.vo.RoleVO;

import java.util.List;

/**
 * 权限service层
 * @author qsyyke
 */

public interface RoleService {
    /**
     * 插入一条权限信息
     * @param roleDO
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    ModifyResult insert(RoleDO roleDO);

    /**
     * 更新权限信息
     * @param roleDO
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    ModifyResult update(RoleDO roleDO);

    /**
     * 删除权限信息
     * @param uid
     * @return
     */
    ModifyResult deleteByUid(int uid);
    /**
     * 根据条件查询满足要求的角色信息
     * @param condition keyword->role
     * @return
     */
    List<RoleVO> queryAllByCondition(ConditionDTO<Integer> condition) throws ReflectiveOperationException;

    /**
     * 根据uid查询权限信息
     * @param uid
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    RoleVO queryByUid(int uid) throws ReflectiveOperationException;
}
