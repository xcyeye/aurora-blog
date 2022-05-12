package xyz.xcye.admin.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.api.feign.EmailFeignService;
import xyz.xcye.admin.dao.UserDao;
import xyz.xcye.admin.dto.EmailVerifyAccountDTO;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.properties.AdminDefaultProperties;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.AccountInfoUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.DateUtils;
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

    @Autowired
    private UserDao userDao;
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
    private UserRedisService userRedisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertUser(User user)
            throws UserException {
        // 判断用户名是否存在
        AssertUtils.stateThrow(!existsUsername(user.getUsername()),
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST));
        // 设置默认属性
        setUserProperties(user);
       return userDao.insertUser(user);
    }

    @Transactional
    @Override
    public int updateUser(User user) throws UserException {
        Objects.requireNonNull(user, "用户信息不能为null");
        if (StringUtils.hasLength(user.getPassword())) {
            // 密码应该单独修改
            user.setPassword(null);
        }

        if (StringUtils.hasLength(user.getUsername()) && existsUsername(user.getUsername())) {
            throw new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST);
        }
        user.setUpdateTime(DateUtils.format(new Date()));
        return userDao.updateUser(user);
    }

    /**
     * 更新密码，忘记密码也会进入此流程，如果是更新密码，那么username,originPwd,newPwd是必须的，如果是忘记密码，需要username，secretKey
     * @param username
     * @param originPwd
     * @param newPwd
     * @param secretKey
     * @return
     */
    @Override
    public int updatePassword(String username, String originPwd, String newPwd, String secretKey) {
        return 0;
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
        return userDao.deleteByUid(uid);
    }

    @Override
    public int logicDeleteByUid(long uid) {
        User user = User.builder().delete(true).uid(uid).build();
        return updateUser(user);
    }

    @Override
    public PageData<UserVO> queryAllByCondition(Condition<Long> condition) {
        return PageUtils.pageList(condition, t -> BeanUtils.copyList(userDao.queryAllByCondition(condition), UserVO.class));
    }

    @Override
    public UserVO queryByUid(long uid) {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(Condition.instant(uid, true)), UserVO.class);
    }

    @Override
    public User queryByUsernameContainPassword(String username) {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(Condition.instant(username, null, null)), User.class);
    }

    @Override
    public User queryByUidContainPassword(long uid) {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(Condition.instant(uid, true, null, null)), User.class);
    }

    @Override
    public UserVO queryByUsername(String username) {
        return BeanUtils.getSingleObjFromList(userDao.queryAllByCondition(Condition.instant(username, null, null)), UserVO.class);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int bindingEmail(long emailUid) throws BindException, EmailException {
        if (emailUid == 0) {
            return 0;
        }

        // 远程调用aurora-message服务，判断此email的uid是否存在
        R r = emailFeignService.queryByUid(emailUid);
        EmailVO queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", EmailVO.class);

        AssertUtils.ifNullThrow(queriedEmailInfo,
                () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS));
        User user = User.builder().emailUid(queriedEmailInfo.getUid()).uid(queriedEmailInfo.getUserUid())
                .updateTime(DateUtils.format(new Date())).build();
        // 判断该用户是否绑定
        UserVO userVO = queryByUid(user.getUid());

        Optional.ofNullable(userVO).orElseThrow(() -> new EmailException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        if (userVO.getVerifyEmail() != null && userVO.getVerifyEmail()) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_HAD_BINDING);
        }

        // 运行到这里，用户没有绑定邮箱，则直接修改，发送，尽管记录里面存在emailUid
        int updateUserNum = userDao.updateUser(user);
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
        return !userDao.queryAllByCondition(condition).isEmpty();
    }

    private void setUserProperties(User user) {
        user.setDelete(false);
        user.setVerifyEmail(false);
        user.setAccountLock(false);
        user.setCreateTime(DateUtils.format(new Date()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUid(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),auroraProperties.getSnowFlakeDatacenterId()));

        if (!StringUtils.hasLength(user.getNickname())) {
            user.setNickname(adminDefaultProperties.getNickname());
        }

        if (!StringUtils.hasLength(user.getAvatar())) {
            user.setAvatar(adminDefaultProperties.getAvatar());
        }

        // 如果没有性别的话，那么默认是秘密(0)
        user.setGender(Optional.ofNullable(user.getGender()).orElse(0));
    }

    private void sendVerifyEmail(UserVO userVO, EmailVO emailVO) throws BindException {
        EmailVerifyAccountDTO verifyAccountInfo = EmailVerifyAccountDTO.builder()
                .userUid(userVO.getUid())
                .expirationTime(auroraAccountProperties.getMailVerifyAccountExpirationTime())
                .verifyAccountUrl(AccountInfoUtils.generateVerifyAccountPath(userVO.getUid(),
                        auroraAccountProperties.getMailVerifyAccountPrefixPath(), userVO.getUsername()))
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
        mailInfo.setCorrelationDataId(GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId()) + "");

        // 运行到这里，直接调用redis进行存储
        userRedisService.storageUserVerifyAccountInfo(verifyAccountInfo, auroraAccountProperties.getMailVerifyAccountExpirationTime());
        sendMQMessageService.sendCommonMail(mailInfo, AmqpExchangeNameConstant.AURORA_SEND_MAIL_EXCHANGE,
                "topic", AmqpQueueNameConstant.SEND_HTML_MAIL_ROUTING_KEY, list);
    }
}
