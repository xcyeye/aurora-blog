package xyz.xcye.admin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.xcye.common.dos.RoleDO;
import xyz.xcye.common.vo.RoleVO;

import java.util.List;

/**
 * @author qsyyke
 */

@Mapper
public interface RoleDao {
    int insert(@Param("roleDO") RoleDO roleDO);
    int update(@Param("roleDO") RoleDO roleDO);
    int deleteByUid(@Param("uid") int uid);
    List<RoleVO> queryAll(@Param("roleDO") RoleDO roleDO);
    RoleVO queryByUid(@Param("uid") int uid);
}
