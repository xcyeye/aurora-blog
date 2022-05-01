package xyz.xcye.admin.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.dao.UserDao;
import xyz.xcye.admin.feign.EmailFeignService;
import xyz.xcye.admin.manager.mq.send.VerifyAccountSendService;
import xyz.xcye.admin.properties.AdminDefaultProperties;
import xyz.xcye.admin.service.RoleService;
import xyz.xcye.admin.service.UserAccountService;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.AccountInfoUtils;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.entity.table.RoleDO;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.JSONUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.vo.EmailVO;
import xyz.xcye.common.vo.UserVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author qsyyke
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private VerifyAccountSendService verifyAccountSendService;
    @Autowired
    private EmailFeignService emailFeignService;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private AuroraProperties.AuroraAccountProperties auroraAccountProperties;
    @Autowired
    private AdminDefaultProperties adminDefaultProperties;

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
        setUserProperties(userDO);
        setUserAccountProperties(userDO,userAccountDO);

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
            // 密码应该单独修改
            userDO.setPassword(null);
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
    public UserDO queryByUsernameContainPassword(String username) throws ReflectiveOperationException {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(ConditionDTO.instant(username, Long.class)), UserDO.class);
    }

    @Override
    public UserDO queryByUidContainPassword(long uid) throws ReflectiveOperationException {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(ConditionDTO.instant(uid, Long.class, true)), UserDO.class);
    }

    @Override
    public UserVO queryByUsername(String username) throws ReflectiveOperationException {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(ConditionDTO.instant(username,Long.class)), UserVO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ModifyResult bindingEmail(long emailUid) throws BindException, EmailException, ReflectiveOperationException {
        if (emailUid == 0) {
            return ModifyResult.operateResult("此邮件的uid不能为0或者null", 0,
                    ResponseStatusCodeEnum.SUCCESS.getCode(), 0);
        }

        // 远程调用aurora-message服务，判断此email的uid是否存在
        R r = emailFeignService.queryByUid(emailUid);
        EmailVO queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", EmailVO.class);
        Optional.ofNullable(queriedEmailInfo).orElseThrow(() -> {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS);
        });

        UserDO userDO = UserDO.builder().emailUid(queriedEmailInfo.getUid()).uid(queriedEmailInfo.getUserUid())
                .updateTime(DateUtils.format(new Date())).build();
        // 判断该用户是否绑定
        UserVO userVO = queryByUid(userDO.getUid());
        if (userVO.getVerifyEmail()) {
            // 已经验证
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING);
        }

        // 如果email_uid已经有了，则直接发送，不用修改
        if (Optional.ofNullable(userVO.getEmailUid()).isPresent()) {
            sendVerifyEmail(userVO,queriedEmailInfo);
        }

        int updateUserNum = userDao.updateUser(userDO);
        if (updateUserNum == 1) {
            sendVerifyEmail(userVO, queriedEmailInfo);
        }

        // 绑定成功 发送验证邮箱
        return ModifyResult.operateResult("绑定链接已发送至您的邮箱",updateUserNum,
                ResponseStatusCodeEnum.SUCCESS.getCode(), queriedEmailInfo.getUserUid());
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
        ConditionDTO<Integer> condition = ConditionDTO.instant(role, Integer.class);
        if (roleService.queryAllByCondition(condition).isEmpty()) {
            // 不存在 新增
            roleService.insert(RoleDO.builder().role(role).build());
        }
        return true;
    }

    private void setUserProperties(UserDO userDO) {
        userDO.setDelete(false);
        userDO.setCreateTime(DateUtils.format(new Date()));
        userDO.setVerifyEmail(false);
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userDO.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));

        if (!StringUtils.hasLength(userDO.getNickname())) {
            userDO.setNickname(adminDefaultProperties.getNickname());
        }

        if (!StringUtils.hasLength(userDO.getAvatar())) {
            userDO.setAvatar(adminDefaultProperties.getAvatar());
        }

        // 如果没有性别的话，那么默认是秘密(0)
        userDO.setGender(Optional.ofNullable(userDO.getGender()).orElse(0));
    }

    private void setUserAccountProperties(UserDO userDO,UserAccountDO userAccountDO) throws ReflectiveOperationException {
        userAccountDO.setCreateTime(DateUtils.format(new Date()));
        userAccountDO.setDelete(false);
        userAccountDO.setAccountExpired(false);
        userAccountDO.setAccountLocked(false);
        userAccountDO.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));
        userAccountDO.setUserUid(userDO.getUid());

        // 判断用户角色是否存在且正确
        userAccountDO.setPermission(getEffectivePermission(userAccountDO.getPermission()));
        if (!existsRole(userAccountDO.getRole())) {
            userAccountDO.setRole(adminDefaultProperties.getRole());
        }
    }

    private void sendVerifyEmail(UserVO userVO, EmailVO emailVO) throws BindException {
        EmailVerifyAccountDTO verifyAccountInfo = EmailVerifyAccountDTO.builder()
                .userUid(userVO.getUid())
                .expirationTime(auroraAccountProperties.getMailVerifyAccountExpirationTime())
                .verifyAccountUrl(AccountInfoUtils.generateVerifyAccountPath(userVO.getUid(),
                        auroraAccountProperties.getMailVerifyAccountPrefixPath(), userVO.getUsername()))
                .receiverEmail(emailVO.getEmail()).subject(null).build();
        verifyAccountSendService.sendVerifyAccount(verifyAccountInfo);
    }
}
