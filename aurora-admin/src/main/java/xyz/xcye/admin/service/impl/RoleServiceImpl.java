package xyz.xcye.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.RoleDao;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.RoleDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.vo.RoleVO;

import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public ModifyResult insert(RoleDO roleDO) {
        roleDO = RoleDO.builder().role(roleDO.getRole())
                .createTime(DateUtils.format(new Date())).build();
        if (roleExists(roleDO.getRole())) {
             return ModifyResult.operateResult("此" + roleDO.getRole() + "已经存在",0,
                    ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
        }
        return ModifyResult.operateResult(roleDao.insert(roleDO),"添加角色信息" + roleDO.getRole(),
                ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
    }

    @Override
    public ModifyResult update(RoleDO roleDO) {
        if (roleExists(roleDO.getRole())) {
            return ModifyResult.operateResult("此" + roleDO.getRole() + "已经存在",0,
                    ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
        }

        return ModifyResult.operateResult(roleDao.update(roleDO),"修改角色信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
    }

    @Override
    public ModifyResult deleteByUid(int uid) {
        return ModifyResult.operateResult(roleDao.deleteByUid(uid),"删除" + uid + "角色信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public List<RoleVO> queryAllByCondition(ConditionDTO<Integer> condition) throws ReflectiveOperationException {
        condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(), condition.getOrderBy());
        return BeanUtils.copyList(roleDao.queryAllByCondition(condition),RoleVO.class);
    }

    @Override
    public RoleVO queryByUid(int uid) throws ReflectiveOperationException {
        ConditionDTO<Integer> condition = ConditionDTO.instant(uid, Integer.class, false);
        return BeanUtils.getSingleObjFromList(roleDao.queryAllByCondition(condition), RoleVO.class);
    }

    /**
     * 判断角色是否存在
     * @param role
     * @return
     */
    private boolean roleExists(String role) {
        ConditionDTO<Integer> condition = ConditionDTO.instant(role, Integer.class);
        List<RoleDO> roleDOList = roleDao.queryAllByCondition(condition);
        return !roleDOList.isEmpty();
    }
}
