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
    /**
     * 插入一条角色记录
     */
    int insert(@Param("roleDO") RoleDO roleDO);

    /**
     * 更新角色记录
     */
    int update(@Param("roleDO") RoleDO roleDO);

    /**
     * 根据uid删除角色记录
     */
    int deleteByUid(@Param("uid") int uid);

    /**
     * 根据条件查询满足要求的角色信息
     * @param condition keyword->role
     * @return
     */
    List<RoleDO> queryAllByCondition(@Param("condition") ConditionDTO<Integer> condition);
}
