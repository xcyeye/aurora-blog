package xyz.xcye.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.xcye.admin.dao.ext.PermissionRelationDaoExt;
import xyz.xcye.admin.dto.RolePermissionDTO;
import xyz.xcye.admin.po.Role;
import xyz.xcye.admin.po.RolePermissionRelationship;
import xyz.xcye.admin.po.UserRoleRelationship;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.permission.PermissionException;
import xyz.xcye.core.exception.role.RoleException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 22:53
 */

@Service
public class PermissionRelationService {

    private final String rolePrefix = "ROLE_";

    @Autowired
    private AuroraUserRoleService auroraUserRoleService;
    @Autowired
    private AuroraRolePermissionService auroraRolePermissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionRelationDaoExt permissionRelationDao;

    public List<RolePermissionDTO> loadPermissionByUserUid(long userUid) {
        return packageCollectResult(permissionRelationDao.loadPermissionByUserUid(userUid));
    }

    public List<Role> loadAllRoleByUsername(String username) {
        UserVO userVO = userService.queryUserByUsername(username);
        AssertUtils.stateThrow(username != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        return permissionRelationDao.loadAllRoleByUserUid(userVO.getUid()).stream()
                .peek(role -> role.setName(rolePrefix + role.getName()))
                .collect(Collectors.toList());
    }

    public List<RolePermissionDTO> loadPermissionByUsername(String username) {
        UserVO userVO = userService.queryUserByUsername(username);
        if (userVO == null) {
            return new ArrayList<>();
        }
        return packageCollectResult(permissionRelationDao.loadPermissionByUserUid(userVO.getUid()));
    }

    public List<RolePermissionDTO> loadPermissionByRoleName(String roleName) {
        return packageCollectResult(permissionRelationDao.loadPermissionByRoleName(roleName));
    }

    public List<RolePermissionDTO> loadAllRolePermission(Condition<Long> condition) {
        return packageCollectResult(permissionRelationDao.loadAllRolePermission(condition));
    }

    public List<RolePermissionDTO> queryRoleByPermissionPath(String permissionPath) {
        return packageCollectResult(permissionRelationDao.queryRoleByPermissionPath(permissionPath));
    }

    @Transactional
    public int insertUserRoleBatch(long[] userUidArr, long roleUid) {
        Role role = roleService.selectByUid(roleUid);
        // 查询看是否存在此角色
        AssertUtils.stateThrow(role != null,
                () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));

        // 判断此角色是否被禁用
        AssertUtils.stateThrow(!role.getStatus(), () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_HAD_DISABLED));

        LongStream longStream = Arrays.stream(userUidArr)
                .filter(userUid -> userService.queryUserByUid(userUid) != null)
                .filter(userUid -> auroraUserRoleService.queryListByCondition(Condition.instant(userUid, roleUid)).getResult().isEmpty());
        longStream.forEach(userUid -> {
            UserRoleRelationship userRoleRelationship = new UserRoleRelationship();
            userRoleRelationship.setRoleUid(roleUid);
            userRoleRelationship.setUserUid(userUid);
            auroraUserRoleService.insert(userRoleRelationship);
        });
        return (int) longStream.count();
    }

    public int deleteUserRoleBatch(long userUid, long[] roleUidArr) {
        final int[] successNum = {0};
        // 判断用户是否存在
        AssertUtils.stateThrow(userService.queryUserByUid(userUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));

        // 组装查询条件
        Condition<Long> condition = new Condition<>();
        condition.setUid(userUid);
        Arrays.stream(roleUidArr).forEach(roleUid -> {
            condition.setOtherUid(roleUid);
            // 查询roleUid和userUid对应的uid
            List<UserRoleRelationship> userRoleRelationshipList = auroraUserRoleService
                    .queryListByCondition(condition).getResult();
            if (userRoleRelationshipList.size() > 0) {
                successNum[0] = successNum[0] + auroraUserRoleService.deleteById(userRoleRelationshipList.get(0).getUid());
            }
        });
        return successNum[0];
    }

    public int updateUserRole(long userUid, long originRoleUid, long newRoleUid) {
        final int[] successNum = {0};
        // 判断用户是否存在
        AssertUtils.stateThrow(userService.queryUserByUid(userUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        // 更新用户角色关系
        Condition<Long> condition = new Condition<>();
        condition.setUid(userUid);
        condition.setOtherUid(originRoleUid);

        List<UserRoleRelationship> userRoleRelationshipList = auroraUserRoleService
                .queryListByCondition(condition).getResult();
        if (userRoleRelationshipList.size() > 0) {
            // 判断newRoleUid是否存在
            AssertUtils.stateThrow(roleService.selectByUid(newRoleUid) != null,
                    () -> new RoleException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
            // 更新
            userRoleRelationshipList.get(0).setRoleUid(newRoleUid);
            successNum[0] = auroraUserRoleService.updateById(userRoleRelationshipList.get(0));
        }

        return successNum[0];
    }

    public int insertRolePermissionBatch(long[] roleUidArr, long permissionUid) {
        final int[] successNum = {0};

        // 判断此permissionUid是否可用
        AssertUtils.stateThrow(permissionService.selectByUid(permissionUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
        Assert.notNull(roleUidArr, "传入的角色uid不能为null");
        LongStream longStream = Arrays.stream(roleUidArr)
                .filter(roleUid -> roleService.selectByUid(roleUid) != null)
                .filter(roleUid -> auroraRolePermissionService.queryListByCondition(Condition.instant(roleUid, permissionUid)).getResult().isEmpty());
        // 遍历可用的roleUid
        longStream.forEach(roleUid -> {
            // 创建角色路径关系
            RolePermissionRelationship rolePermissionRelationship = RolePermissionRelationship.builder()
                    .permissionUid(permissionUid)
                    .roleUid(roleUid)
                    .build();
                    // 插入角色路径关系
                    auroraRolePermissionService.insert(rolePermissionRelationship);
                });
        return (int) longStream.count();
    }

    public int deleteRolePermissionBatch(long roleUid, long[] permissionUidArr) {
        final int[] successNum = {0};
        // 判断角色是否存在
        AssertUtils.stateThrow(roleService.selectByUid(roleUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
        // 组装查询条件
        Condition<Long> condition = new Condition<>();
        condition.setUid(roleUid);
        Arrays.stream(permissionUidArr).forEach(permissionUid -> {
            // 查询出roleUid和permissionUid对应的uid
            condition.setOtherUid(permissionUid);
            List<RolePermissionRelationship> rolePermissionRelationshipList = auroraRolePermissionService.queryListByCondition(condition).getResult();
            if (rolePermissionRelationshipList.size() > 0) {
                // 删除
                successNum[0] = successNum[0] + auroraRolePermissionService.deleteById(rolePermissionRelationshipList.get(0).getUid());
            }
        });

        return successNum[0];
    }

    public int updateRolePermission(long roleUid, long originPermissionUid, long newPermissionUid) {
        final int[] successNum = {0};
        // 判断角色是否存在
        AssertUtils.stateThrow(roleService.selectByUid(roleUid) != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_ROLE_NOT_EXISTS));
        // 更新用户角色关系
        Condition<Long> condition = new Condition<>();
        condition.setUid(roleUid);
        condition.setOtherUid(originPermissionUid);
        List<RolePermissionRelationship> permissionRelationshipList = auroraRolePermissionService.queryListByCondition(condition).getResult();
        if (permissionRelationshipList.size() > 0) {
            // 判断此newPermissionUid是否存在
            AssertUtils.stateThrow(permissionService.selectByUid(newPermissionUid) != null,
                    () -> new PermissionException(ResponseStatusCodeEnum.PERMISSION_RESOURCE_NOT_RIGHT));
            // 更新
            permissionRelationshipList.get(0).setPermissionUid(newPermissionUid);
            successNum[0] = auroraRolePermissionService.updateById(permissionRelationshipList.get(0));
        }
        return successNum[0];
    }

    /**
     * 因为存放在数据库中的角色名没有加上前缀ROLE_，所以统一在这里，对返回结果的角色名，加上前缀
     * @param rolePermissionDTOList
     * @return
     */
    private List<RolePermissionDTO> packageCollectResult(List<RolePermissionDTO> rolePermissionDTOList) {
        return rolePermissionDTOList.stream()
                .peek(rolePermissionDTO -> rolePermissionDTO.setRoleName(rolePrefix + rolePermissionDTO.getRoleName()))
                .collect(Collectors.toList());
    }
}
