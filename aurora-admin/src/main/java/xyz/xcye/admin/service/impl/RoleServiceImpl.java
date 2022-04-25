package xyz.xcye.admin.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.xcye.admin.dao.RoleDao;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.common.entity.table.RoleDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
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

    @Autowired
    private RoleDao roleDao;

    @Override
    public ModifyResult insert(RoleDO roleDO) throws InstantiationException, IllegalAccessException {
        roleDO = RoleDO.builder().role(roleDO.getRole())
                .createTime(DateUtils.format(new Date())).build();
        if (roleExists(roleDO.getRole())) {
             return ModifyResult.operateResult("此" + roleDO.getRole() + "已经存在",0,
                    ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
        }

        int insertNum = roleDao.insert(roleDO);
        return ModifyResult.operateResult(insertNum,"添加角色信息" + roleDO.getRole(),
                ResponseStatusCodeEnum.SUCCESS.getCode(), roleDO.getUid());
    }

    @Override
    public ModifyResult update(RoleDO roleDO) throws InstantiationException, IllegalAccessException {
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
    public List<RoleVO> queryAll(RoleDO roleDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        paginationDTO = PaginationDTO.initPagination(paginationDTO,defaultPageNum,defaultPageSize);
        PageHelper.startPage(paginationDTO.getPageNum(),paginationDTO.getPageSize(), paginationDTO.getOrderBy());
        return BeanUtils.copyList(roleDao.queryAll(roleDO),RoleVO.class);
    }

    @Override
    public RoleVO queryByUid(int uid) throws InstantiationException, IllegalAccessException {
        return BeanUtils.copyProperties(roleDao.queryByUid(uid),RoleVO.class);
    }

    /**
     * 判断角色是否存在
     * @param role
     * @return
     */
    private boolean roleExists(String role) throws InstantiationException, IllegalAccessException {
        if (!queryAll(RoleDO.builder().role(role).build(),null).isEmpty()) {
            return true;
        }

        return false;
    }
}
