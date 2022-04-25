package xyz.xcye.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.UserAccountDao;
import xyz.xcye.admin.service.UserAccountService;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.vo.UserAccountVO;

import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class UserAccountServiceImpl implements UserAccountService {

    /**
     * 查询时默认的初始页数
     */
    @Value("${aurora.pagination.pageNum}")
    private int defaultPageNum;

    /**
     * 查询时默认的返回数目
     */
    @Value("${aurora.pagination.pageSize}")
    private int defaultPageSize;

    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public ModifyResult insert(UserAccountDO userAccountDO) {
        userAccountDO = UserAccountDO.builder()
                .uid(GenerateInfoUtils.generateUid(workerId,datacenterId))
                .createTime(DateUtils.format(new Date())).delete(false)
                .accountLocked(false).accountExpired(false)
                .role(userAccountDO.getRole()).permission(userAccountDO.getPermission())
                .userUid(userAccountDO.getUserUid()).build();
        return ModifyResult.operateResult(userAccountDao.insert(userAccountDO),"插入账户信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(),userAccountDO.getUid());
    }

    @Override
    public ModifyResult update(UserAccountDO userAccountDO) {
        userAccountDO.setUpdateTime(DateUtils.format(new Date()));
        return ModifyResult.operateResult(userAccountDao.update(userAccountDO),"修改账户信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(), userAccountDO.getUid());
    }

    @Override
    public ModifyResult updateDeleteStatus(UserAccountDO userAccountDO) {
        userAccountDO.setUpdateTime(DateUtils.format(new Date()));
        return ModifyResult.operateResult(userAccountDao.update(userAccountDO),"修改账户删除状态",
                ResponseStatusCodeEnum.SUCCESS.getCode(), userAccountDO.getUid());
    }

    @Override
    public ModifyResult deleteByUid(long uid) {
        return ModifyResult.operateResult(userAccountDao.deleteByUid(uid),"删除账户信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public List<UserAccountVO> queryAll(UserAccountDO userAccountDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        paginationDTO = PaginationDTO.initPagination(paginationDTO,defaultPageNum,defaultPageSize);
        return BeanUtils.copyList(userAccountDao.queryAll(userAccountDO,paginationDTO),UserAccountVO.class);
    }

    @Override
    public UserAccountVO queryByUid(long uid) throws InstantiationException, IllegalAccessException {
        return BeanUtils.copyProperties(userAccountDao.queryByUid(uid),UserAccountVO.class);
    }

    @Override
    public UserAccountVO queryByUserUid(long userUid) throws InstantiationException, IllegalAccessException {
        return BeanUtils.copyProperties(userAccountDao.queryByUserUid(userUid),UserAccountVO.class);
    }
}
