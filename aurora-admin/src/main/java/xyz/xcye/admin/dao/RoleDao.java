package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.table.RoleDO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface RoleDao {
    int insert(@Param("roleDO") RoleDO roleDO);
    int update(@Param("roleDO") RoleDO roleDO);
    int deleteByUid(@Param("uid") int uid);

    /**
     * 根据条件查询满足要求的角色信息
     * @param condition keyword->role
     * @return
     */
    List<RoleDO> queryAllByCondition(@Param("condition") ConditionDTO condition);
}
