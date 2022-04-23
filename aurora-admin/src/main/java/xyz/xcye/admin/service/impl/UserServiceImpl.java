package xyz.xcye.admin.service.impl;

import com.github.pagehelper.PageHelper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.dao.UserDao;
import xyz.xcye.admin.entity.DefaultValueEntity;
import xyz.xcye.admin.util.AccountInfoUtils;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.admin.manager.mq.AdminRabbitMQSendService;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.admin.service.UserAccountService;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.UserAccountVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.common.dos.RoleDO;
import xyz.xcye.common.dos.UserAccountDO;
import xyz.xcye.common.dos.UserDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.BeanCopyUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */

@Service
public class UserServiceImpl implements UserService {

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

    @Value("${aurora.user.remember-me-day}")
    private int rememberMeDay;

    @Value("${aurora.user.default-role}")
    private String defaultRole;

    @Value("${aurora.user.default-permission}")
    private String defaultPermission;

    @Value("${aurora.admin.verify.account.expiration-time}")
    private int emailVerifyAccountExpirationTime;

    @Value("${aurora.admin.verify.account.email-prefix-path}")
    private String emailVerifyAccountPrefixPath;

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DefaultValueEntity defaultValueEntity;
    @Autowired
    private AdminRabbitMQSendService adminRabbitMQSendService;
    @Autowired
    private MessageLogFeignService messageLogFeignService;

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public ModifyResult insertUser(UserDO userDO, UserAccountDO userAccountDO, EmailDO emailDO) throws UserException, InstantiationException, IllegalAccessException, BindException, EmailException {
        // 判断用户名是否存在
        if (existsUsername(userDO.getUsername())) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getMessage(),
                    0,ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getCode());
        }

        // 设置默认属性
        userDO = setUserProperties(userDO,emailDO);
        userAccountDO = setUserAccountProperties(userDO,userAccountDO);

        // 先远程调用插入邮箱
        emailDO.setUserUid(userDO.getUid());
        ModifyResult insertModifyResult = messageLogFeignService.insertEmail(emailDO);
        EmailDO queriedEmailDO = messageLogFeignService.queryByUid(insertModifyResult.getUid());
        if (queriedEmailDO == null) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS.getMessage(),
                    ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS.getCode());
        }

        // 插入
        int insertUserNum = userDao.insertUser(userDO);
        ModifyResult modifyResult = userAccountService.insert(userAccountDO);
        if (modifyResult.getAffectedRows() == 1) {
            userDO = UserDO.builder().uid(userDO.getUid()).userAccountUid(modifyResult.getUid()).build();
        }else {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD.getCode());
        }

        if (updateUser(userDO).getAffectedRows() != 1) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD.getCode());
        }
        // 执行到这里，插入用户成功，发送验证账户的邮件
        EmailVerifyAccountDTO verifyAccountDTO = EmailVerifyAccountDTO.builder().verifyAccountUrl(AccountInfoUtils.generateVerifyAccountPath(userDO.getUid(),
                        emailVerifyAccountPrefixPath)).expirationTime(new Date().getTime() + emailVerifyAccountExpirationTime)
                .receiverEmail(emailDO.getEmail()).build();

        adminRabbitMQSendService.sendVerifyAccount(verifyAccountDTO, userDO);

        return ModifyResult.operateResult(insertUserNum,"插入用户" + userDO.getUsername(),
                ResponseStatusCodeEnum.SUCCESS.getCode(),userDO.getUid());
    }

    @Override
    public ModifyResult updateUser(UserDO userDO) throws UserException {
        if (StringUtils.hasLength(userDO.getPassword())) {
            userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        }

        if (StringUtils.hasLength(userDO.getUsername()) && existsUsername(userDO.getUsername())) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getCode());
        }

        userDO.setUpdateTime(DateUtils.format(new Date()));
        if (userDO.getUserAccountUid() != null) {
            // 用户的权限账户信息应该单独更新
            userDO.setUserAccountUid(null);
        }

        int updateUserNum = userDao.updateUser(userDO);
        return ModifyResult.operateResult(updateUserNum,"更新" + userDO.getUid() + "用户信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(),userDO.getUid());
    }

    @Override
    public ModifyResult updateDeleteStatus(UserDO userDO) throws UserException, InstantiationException, IllegalAccessException {
        // 先查询此用户对应的account
        UserAccountVO queriedUserAccountVO = userAccountService.queryByUserUid(userDO.getUid());
        if (queriedUserAccountVO == null) {
            return ModifyResult.operateResult(userDO.getUid() + ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getMessage(),
                    0,ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getCode());
        }

        userDO = UserDO.builder()
                .delete(userDO.getDelete()).uid(userDO.getUid())
                .updateTime(DateUtils.format(new Date())).build();
        UserAccountDO userAccountDO = UserAccountDO.builder()
                .uid(queriedUserAccountVO.getUid()).delete(userDO.getDelete())
                .updateTime(DateUtils.format(new Date())).build();
        int updateUserDeleteStatusNum = userDao.updateDeleteStatus(userDO);
        int updateUserAccountDeleteStatusNum = userAccountService.updateDeleteStatus(userAccountDO).getAffectedRows();
        if (updateUserDeleteStatusNum != updateUserAccountDeleteStatusNum) {
            throw new UserException("更新" + userDO.getUid() + "用户删除状态失败",
                    ResponseStatusCodeEnum.SUCCESS.getCode());
        }

        return ModifyResult.operateResult("更新" + userDO.getUid() + "用户删除状态",
                1,ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Transactional
    @Override
    public ModifyResult deleteByUid(long uid) throws UserException, InstantiationException, IllegalAccessException {
        UserAccountVO userAccountVO = userAccountService.queryByUserUid(uid);
        if (userAccountVO == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getCode());
        }
        int deleteUserNum = userDao.deleteByUid(uid);
        ModifyResult deleteUserAccountResult = userAccountService.deleteByUid(userAccountVO.getUid());

        if (deleteUserAccountResult.getAffectedRows() != deleteUserNum) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_DELETE.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_FAIL_DELETE.getCode());
        }
        return ModifyResult.operateResult("删除" + uid + "用户",1,ResponseStatusCodeEnum.SUCCESS.getCode());
    }

    @Override
    public List<UserVO> queryAll(UserDO userDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        paginationDTO = PaginationDTO.initPagination(paginationDTO,defaultPageNum,defaultPageSize);
        PageHelper.startPage(paginationDTO.getPageNum(),paginationDTO.getPageSize(),paginationDTO.getOrderBy());
        List<UserDO> userDOList = userDao.queryAll(userDO);
        return BeanCopyUtils.copyList(userDOList,UserVO.class);
    }

    @Override
    public UserVO queryByUid(long uid) throws InstantiationException, IllegalAccessException {
        return BeanCopyUtils.copyProperties(userDao.queryByUid(uid),UserVO.class);
    }

    @Override
    public UserDO queryByUsername(String username) {
        return userDao.queryByUsername(username);
    }

    /**
     * 获取有效的权限信息
     * @param permission
     * @return
     */
    private String getEffectivePermission(String permission) throws InstantiationException, IllegalAccessException {
        if (!StringUtils.hasLength(permission)) {
            return null;
        }

        String[] permissionArr = permission.split(",");
        permission = "";
        for (String splitPermission : permissionArr) {
            if (StringUtils.hasLength(splitPermission)) {
                if (existsRole(splitPermission)) {
                    permission = permission + splitPermission + ",";
                }
            }
        }
        permission = permission.substring(0,permission.length() - 1);
        return permission;
    }

    /**
     * 判断传入的用户名是否已经存在
     * @param username
     * @return
     */
    private boolean existsUsername(String username) {
        UserDO userDO = UserDO.builder()
                .username(username).build();
        return !userDao.queryAll(userDO).isEmpty();
    }

    /**
     * 判断role是否存在于数据库中
     * @param role
     * @return
     */
    private boolean existsRole(String role) throws InstantiationException, IllegalAccessException {
        if (!StringUtils.hasLength(role)) {
            return false;
        }
        RoleDO roleDO = RoleDO.builder().role(role).build();
        if (roleService.queryAll(roleDO, null).isEmpty()) {
            // 不存在 新增
            roleService.insert(RoleDO.builder().role(role).build());
        }
        return true;
    }

    private UserDO setUserProperties(UserDO userDO, EmailDO emailDO) {
        userDO.setCreateTime(DateUtils.format(new Date()));
        userDO.setDelete(false);
        userDO.setVerifyEmail(false);
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));
        userDO.setEmailUid(emailDO.getUid());

        if (!StringUtils.hasLength(userDO.getNickname())) {
            userDO.setNickname(defaultValueEntity.getNickname());
        }

        if (!StringUtils.hasLength(userDO.getAvatar())) {
            userDO.setAvatar(defaultValueEntity.getAvatar());
        }

        // 部分属性值缺失，设置默认值
        if (!StringUtils.hasLength(userDO.getGender())) {
            userDO.setGender(defaultValueEntity.getGender());
        }

        return userDO;
    }

    private UserAccountDO setUserAccountProperties(UserDO userDO,UserAccountDO userAccountDO) throws InstantiationException, IllegalAccessException {
        userAccountDO.setCreateTime(DateUtils.format(new Date()));
        userAccountDO.setDelete(false);
        userAccountDO.setAccountExpired(false);
        userAccountDO.setAccountLocked(false);
        userAccountDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));
        userAccountDO.setUserUid(userDO.getUid());

        // 判断用户角色是否存在且正确
        userAccountDO.setPermission(getEffectivePermission(userAccountDO.getPermission()));
        if (!existsRole(userAccountDO.getRole())) {
            userAccountDO.setRole(defaultValueEntity.getRole());
        }

        return userAccountDO;
    }
}
