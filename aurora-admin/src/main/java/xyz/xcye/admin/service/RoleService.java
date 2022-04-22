package xyz.xcye.admin.service;

import xyz.xcye.admin.vo.RoleVO;
import xyz.xcye.common.dos.RoleDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;

import java.util.List;

/**
 * 权限service层
 * @author qsyyke
 */

public interface RoleService {
    ModifyResult insert(RoleDO roleDO) throws InstantiationException, IllegalAccessException;
    ModifyResult update(RoleDO roleDO) throws InstantiationException, IllegalAccessException;
    ModifyResult deleteByUid(int uid);
    List<RoleVO> queryAll(RoleDO roleDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException;
    RoleVO queryByUid(int uid) throws InstantiationException, IllegalAccessException;
}
