package xyz.xcye.admin.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.api.feign.EmailFeignService;
import xyz.xcye.admin.dao.UserMapper;
import xyz.xcye.admin.dto.EmailVerifyAccountDTO;
import xyz.xcye.admin.enums.GenderEnum;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.properties.AdminDefaultProperties;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.AccountInfoUtils;
import xyz.xcye.api.mail.sendmail.util.StorageEmailVerifyUrlUtil;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.auth.constant.AuthRedisConstant;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.data.util.PageUtils;
import xyz.xcye.message.vo.EmailVO;

import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final String bindEmailKey = "bindEmail";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailFeignService emailFeignService;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private AuroraProperties.AuroraAccountProperties auroraAccountProperties;
    @Autowired
    private AdminDefaultProperties adminDefaultProperties;
    @Autowired
    private SendMQMessageService sendMQMessageService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertUserSelective(User user)
            throws UserException {
        // 判断用户名是否存在
        AssertUtils.stateThrow(!existsUsername(user.getUsername()),
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST));
        // 设置默认属性
        setUserProperties(user);
       return userMapper.insertSelective(user);
    }

    @Transactional
    @Override
    public int updateUserSelective(User user) throws UserException {
        Objects.requireNonNull(user, "用户信息不能为null");
        // 密码应该单独修改
        Optional.ofNullable(user.getPassword()).ifPresent(t -> user.setPassword(null));

        if (StringUtils.hasLength(user.getUsername()) && existsUsername(user.getUsername())) {
            //throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST);
            // 用户名存在，不修改用户名
            user.setUsername(null);
        }
        // 保存原始的用户名，如果此userUid不存在，返回空字符
        String username = getUsername(user.getUid());
        int updateNum = userMapper.updateByPrimaryKeySelective(user);
        if (updateNum == 1) {
            removeUserDetailsFromRedis(username);
        }
        return updateNum;
    }

    /**
     * 更新密码，忘记密码也会进入此流程，如果是更新密码，那么username,originPwd,newPwd是必须的，如果是忘记密码，需要username，secretKey
     * @param username
     * @param originPwd
     * @param newPwd
     * @return
     */
    @Override
    public int updatePassword(String username, String originPwd, String newPwd) {
        AssertUtils.stateThrow(StringUtils.hasLength(username), () -> new UserException("用户名不能为空"));
        // 查询此用户的原始密码
        User user = queryByUsernameContainPassword(username);
        AssertUtils.stateThrow(user != null, () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        // 查看密码是否匹配
        boolean matches = passwordEncoder.matches(user.getPassword(), originPwd);
        AssertUtils.stateThrow(matches, () -> new UserException("密码错误"));

        // 修改密码
        user.setPassword(passwordEncoder.encode(newPwd));
        int updateNum = userMapper.updateByPrimaryKeySelective(user);
        if (updateNum == 1) {
            removeUserDetailsFromRedis(user.getUsername());
        }
        return updateNum;
    }

    /**
     * 忘记密码，先验证此用户名是否存在，如果存在，远程调用，查看此用户是否绑定了email，如果没有email，则不支持找回，如果存在，则发送重置密码
     * 的html到该email中
     * @param username
     * @return
     */
    @Override
    public int forgotPassword(String username) {

        return 0;
    }

    @Transactional
    @Override
    public int realDeleteByUid(long uid) {
        return userMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int logicDeleteByUid(long uid) {
        User user = User.builder().delete(true).uid(uid).build();
        return updateUserSelective(user);
    }

    @Override
    public PageData<UserVO> queryAllByCondition(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> userMapper.selectByCondition(condition), UserVO.class);
    }

    @Override
    public UserVO queryUserByUid(long uid) {
        return BeanUtils.getSingleObjFromList(userMapper.selectByCondition(Condition.instant(uid, true)), UserVO.class);
    }

    @Override
    public User queryByUsernameContainPassword(String username) {
        return BeanUtils.getSingleObjFromList(userMapper.selectByCondition(Condition.instant(username, null, null)), User.class);
    }

    @Override
    public User queryByUidContainPassword(long uid) {
        return BeanUtils.getSingleObjFromList(userMapper.selectByCondition(Condition.instant(uid, true, null, null)), User.class);
    }

    @Override
    public UserVO queryUserByUsername(String username) {
        return BeanUtils.getSingleObjFromList(userMapper.selectByCondition(Condition.instant(username, null, null)), UserVO.class);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int bindingEmail(String email) throws BindException, EmailException {
        AssertUtils.stateThrow(StringUtils.hasLength(email), () -> new EmailException(ResponseStatusCodeEnum.PARAM_IS_INVALID));
        // 远程调用aurora-message服务，判断此email的uid是否存在
        R r = emailFeignService.queryByEmail(email);
        EmailVO queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", EmailVO.class);

        AssertUtils.ifNullThrow(queriedEmailInfo,
                () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS));
        AssertUtils.ifNullThrow(queriedEmailInfo.getEmail(),
                () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS));
        User user = User.builder()
                .emailUid(queriedEmailInfo.getUid())
                .uid(queriedEmailInfo.getUserUid())
                .build();
        // 判断该用户是否绑定
        UserVO userVO = queryUserByUid(user.getUid());
        AssertUtils.stateThrow(!userVO.getVerifyEmail(), () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING));
        // 判断该用户是否被删除
        AssertUtils.stateThrow(!userVO.getDelete(), () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_DELETE));
        if (userVO.getVerifyEmail() != null && userVO.getVerifyEmail()) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING);
        }

        // 运行到这里，用户没有绑定邮箱，则直接修改，发送，尽管记录里面存在emailUid
        int updateUserNum = updateUserSelective(user);
        if (updateUserNum == 1) {
            sendVerifyEmail(userVO, queriedEmailInfo);
        }

        // 调通redis进行存储
        return updateUserNum;
    }

    /**
     * 判断传入的用户名是否已经存在
     * @param username
     * @return
     */
    private boolean existsUsername(String username) {
        Condition<Long> condition = Condition.instant(username, null, null);
        return !userMapper.selectByCondition(condition).isEmpty();
    }

    private String getUsername(Long userUid) {
        UserVO userVO = queryUserByUid(userUid);
        return userVO == null ? "" : userVO.getUsername();
    }

    private void setUserProperties(User user) {
        user.setDelete(false);
        user.setVerifyEmail(false);
        user.setAccountLock(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));

        if (!StringUtils.hasLength(user.getNickname())) {
            user.setNickname(adminDefaultProperties.getNickname());
        }

        if (!StringUtils.hasLength(user.getAvatar())) {
            user.setAvatar(adminDefaultProperties.getAvatar());
        }

        // 如果没有性别的话，那么默认是秘密(0)
        user.setGender(Optional.ofNullable(user.getGender()).orElse(GenderEnum.SECRET));
    }

    private void sendVerifyEmail(UserVO userVO, EmailVO emailVO) throws BindException {
        String verifyAccountUrl = AccountInfoUtils.generateVerifyUrl(userVO.getUid(),
                bindEmailKey, userVO.hashCode(), auroraAccountProperties.getMailVerifyAccountPrefixPath());
        EmailVerifyAccountDTO verifyAccountInfo = EmailVerifyAccountDTO.builder()
                .userUid(userVO.getUid())
                .expirationTime(auroraAccountProperties.getMailVerifyAccountExpirationTime())
                .verifyAccountUrl(verifyAccountUrl)
                .receiverEmail(emailVO.getEmail()).subject(null).build();

        List<Map<SendHtmlMailTypeNameEnum, Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum, Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.VERIFY_ACCOUNT, verifyAccountInfo);
        list.add(map);

        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setReceiverEmail(emailVO.getEmail());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.VERIFY_ACCOUNT);
        mailInfo.setSubject(userVO.getUsername() + " 请验证你的账户信息");
        mailInfo.setUserUid(userVO.getUid());

        // 运行到这里，直接调用redis进行存储
        boolean storageVerifyUrlToRedis = StorageEmailVerifyUrlUtil.storageVerifyUrlToRedis(bindEmailKey, userVO.hashCode(),
                auroraAccountProperties.getMailVerifyAccountExpirationTime(), userVO.getUid());
        if (!storageVerifyUrlToRedis) {
            throw new UserException("绑定邮箱失败");
        }
        sendMQMessageService.sendCommonMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, list);
    }

    private void removeUserDetailsFromRedis(String username) {
        // 用户名修改，删除redis中的userService缓存
        redisTemplate.delete(AuthRedisConstant.USER_DETAILS_CACHE_PREFIX + username);
    }
}
