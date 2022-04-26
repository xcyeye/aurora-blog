package xyz.xcye.admin.service.impl;

import com.github.pagehelper.PageHelper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.dao.UserDao;
import xyz.xcye.admin.entity.DefaultValueEntity;
import xyz.xcye.admin.manager.mq.send.VerifyAccountSendService;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.admin.service.UserAccountService;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.util.AccountInfoUtils;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.EmailDO;
import xyz.xcye.common.entity.table.RoleDO;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.vo.UserVO;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;

import java.util.Date;
import java.util.List;

/**
 * @author qsyyke
 */

@Slf4j
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
    private MessageLogFeignService messageLogFeignService;
    @Autowired
    private VerifyAccountSendService verifyAccountSendService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ModifyResult insertUser(UserDO userDO, UserAccountDO userAccountDO)
            throws UserException, ReflectiveOperationException {
        // 判断用户名是否存在
        if (existsUsername(userDO.getUsername())) {
            return ModifyResult.operateResult(ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getMessage(),
                    0,ResponseStatusCodeEnum.PERMISSION_USER_EXIST.getCode(), 0);
        }

        // 设置默认属性
        userDO = setUserProperties(userDO);
        userAccountDO = setUserAccountProperties(userDO,userAccountDO);

        // 插入
        int insertUserNum = userDao.insertUser(userDO);
        ModifyResult modifyResult = userAccountService.insert(userAccountDO);

        // 保存临时的用户名
        String usernameTemp = userDO.getUsername();
        if (modifyResult.isSuccess()) {
            // 因为userAccountDO的uid只有插入成功之后，才知道，所以需要调用修改方法进行修改
            userDO = UserDO.builder().uid(userDO.getUid()).userAccountUid(modifyResult.getUid()).build();
        }else {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD);
        }

        if (!updateUser(userDO).isSuccess()) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_ADD);
        }

        return ModifyResult.operateResult(insertUserNum,"插入用户" + usernameTemp,
                ResponseStatusCodeEnum.SUCCESS.getCode(),userDO.getUid());
    }

    @Transactional
    @Override
    public ModifyResult updateUser(UserDO userDO) throws UserException {
        if (StringUtils.hasLength(userDO.getPassword())) {
            userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        }

        if (StringUtils.hasLength(userDO.getUsername()) && existsUsername(userDO.getUsername())) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST);
        }

        userDO.setUpdateTime(DateUtils.format(new Date()));

        int updateUserNum = userDao.updateUser(userDO);
        return ModifyResult.operateResult(updateUserNum,"更新" + userDO.getUid() + "用户信息",
                ResponseStatusCodeEnum.SUCCESS.getCode(),userDO.getUid());
    }

    @Transactional
    @Override
    public ModifyResult deleteByUid(long uid) {

        /*UserAccountVO userAccountVO = userAccountService.queryByUserUid(uid);
        if (userAccountVO == null) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST.getCode());
        }
        int deleteUserNum = userDao.deleteByUid(uid);
        ModifyResult deleteUserAccountResult = userAccountService.deleteByUid(userAccountVO.getUid());

        if (!deleteUserAccountResult.isSuccess()) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_FAIL_DELETE.getMessage(),
                    ResponseStatusCodeEnum.PERMISSION_USER_FAIL_DELETE.getCode());
        }*/
        int deleteUserNum = userDao.deleteByUid(uid);
        return ModifyResult.operateResult("删除" + uid + "用户",deleteUserNum,ResponseStatusCodeEnum.SUCCESS.getCode(), uid);
    }

    @Override
    public List<UserVO> queryAllByCondition(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        condition.init(condition);
        PageHelper.startPage(condition.getPageNum(),condition.getPageSize(),condition.getOrderBy());
        return BeanUtils.copyList(userDao.queryAllByCondition(condition), UserVO.class);
    }

    @Override
    public UserVO queryByUid(long uid) throws ReflectiveOperationException {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(ConditionDTO.instant(uid, Long.class, true)), UserVO.class);
    }

    @Override
    public UserVO queryByUsername(String username) throws ReflectiveOperationException {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(ConditionDTO.instant(username,Long.class)), UserVO.class);
    }

    @GlobalTransactional
    @Override
    public ModifyResult bindingEmail(EmailDO emailDO) throws BindException {
        UserDO userDO = UserDO.builder().emailUid(emailDO.getUid()).uid(emailDO.getUserUid())
                .updateTime(DateUtils.format(new Date())).build();

        int updateUserNum = userDao.updateUser(userDO);
        log.info("绑定邮箱,用户uid:{},邮箱信息{},绑定结果:{}",userDO.getUid(),emailDO,updateUserNum);
        if (updateUserNum == 1) {
            EmailVerifyAccountDTO verifyAccountInfo = EmailVerifyAccountDTO.builder()
                    .userUid(userDO.getUid())
                    .expirationTime((long) emailVerifyAccountExpirationTime)
                    .verifyAccountUrl(AccountInfoUtils.generateVerifyAccountPath(userDO.getUid(), emailVerifyAccountPrefixPath))
                    .receiverEmail(emailDO.getEmail()).subject(null).build();
            verifyAccountSendService.sendVerifyAccount(verifyAccountInfo);
        }

        // 绑定成功 发送验证邮箱
        return ModifyResult.operateResult(updateUserNum, "绑定邮箱",
                ResponseStatusCodeEnum.SUCCESS.getCode(), emailDO.getUserUid());
    }

    /**
     * 获取有效的权限信息
     * @param permission
     * @return
     */
    private String getEffectivePermission(String permission) throws ReflectiveOperationException {
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
        return !userDao.queryAllByCondition(ConditionDTO.instant(username,Long.class)).isEmpty();
    }

    /**
     * 判断role是否存在于数据库中
     * @param role
     * @return
     */
    private boolean existsRole(String role) throws ReflectiveOperationException {
        if (!StringUtils.hasLength(role)) {
            return false;
        }
        ConditionDTO<Long> condition = ConditionDTO.instant(role, Long.class);
        if (roleService.queryAllByCondition(condition).isEmpty()) {
            // 不存在 新增
            roleService.insert(RoleDO.builder().role(role).build());
        }
        return true;
    }

    private UserDO setUserProperties(UserDO userDO) {
        userDO.setCreateTime(DateUtils.format(new Date()));
        userDO.setVerifyEmail(false);
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userDO.setUid(GenerateInfoUtils.generateUid(workerId,datacenterId));

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

    private UserAccountDO setUserAccountProperties(UserDO userDO,UserAccountDO userAccountDO) throws ReflectiveOperationException {
        userAccountDO.setCreateTime(DateUtils.format(new Date()));
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
