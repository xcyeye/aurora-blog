package xyz.xcye.admin.service;


import xyz.xcye.common.dos.UserAccountDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.UserAccountVO;

import java.util.List;

/**
 * 用户权限的servie层
 * @author qsyyke
 */

public interface UserAccountService {
    ModifyResult insert(UserAccountDO userAccountDO);

    ModifyResult update(UserAccountDO userAccountDO);

    ModifyResult updateDeleteStatus(UserAccountDO userAccountDO);

    ModifyResult deleteByUid(long uid);

    List<UserAccountVO> queryAll(UserAccountDO userAccountDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException;

    UserAccountVO queryByUid(long uid) throws InstantiationException, IllegalAccessException;

    UserAccountVO queryByUserUid(long userUid) throws InstantiationException, IllegalAccessException;
}
