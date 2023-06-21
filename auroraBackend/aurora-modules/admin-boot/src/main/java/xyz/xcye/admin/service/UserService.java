package xyz.xcye.admin.service;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.admin.api.feign.ArticleFeignService;
import xyz.xcye.admin.api.feign.EmailFeignService;
import xyz.xcye.admin.dto.EmailVerifyAccountDTO;
import xyz.xcye.admin.enums.GenderEnum;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.pojo.RolePermissionRelationshipPojo;
import xyz.xcye.admin.pojo.RolePojo;
import xyz.xcye.admin.pojo.SiteSettingPojo;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.admin.properties.AdminDefaultProperties;
import xyz.xcye.admin.vo.RoleVO;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.amqp.comstant.AmqpExchangeNameConstant;
import xyz.xcye.amqp.comstant.AmqpQueueNameConstant;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.AccountInfoUtils;
import xyz.xcye.api.mail.sendmail.util.StorageEmailVerifyUrlUtil;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.UserUtils;
import xyz.xcye.auth.constant.AuthRedisConstant;
import xyz.xcye.core.dto.JwtUserInfo;
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
import xyz.xcye.message.pojo.EmailPojo;
import xyz.xcye.message.vo.EmailVO;

import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Service
public class UserService {

    private final String bindEmailKey = "bindEmail";
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
    @Autowired
    private AuroraUserService auroraUserService;
    @Autowired
    private AuroraSettingService auroraSettingService;
    @Autowired
    private SiteSettingService siteSettingService;

    @Autowired
    private PermissionRelationService permissionRelationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ArticleFeignService articleFeignService;

    @Autowired
    private AuroraProperties.AuroraDefaultUserInfoProperties auroraDefaultUserInfoProperties;

    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserPojo user) throws UserException {
        // 判断用户名是否存在
        AssertUtils.stateThrow(!existsUsername(user.getUsername()),
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_EXIST));
        // 设置默认属性
        setUserProperties(user);
        auroraUserService.insert(BeanUtils.copyProperties(user, User.class));
        // 插入成功之后，设置默认用户角色

        List<String> defaultRoleList = auroraProperties.getDefaultRoleList();
        if (defaultRoleList == null || defaultRoleList.isEmpty()) {
            return;
        }

        defaultRoleList.forEach(roleName -> {
            RolePojo rolePojo = new RolePojo();
            rolePojo.setName(roleName);
            RoleVO roleVO = roleService.queryOneRole(rolePojo);
            if (roleVO == null) {
                return;
            }
            RolePermissionRelationshipPojo relationshipPojo = new RolePermissionRelationshipPojo();
            relationshipPojo.setUserUidArr(Collections.singletonList(user.getUid()));
            relationshipPojo.setRoleUidArr(Collections.singletonList(roleVO.getUid()));
            permissionRelationService.insertUserRoleBatch(relationshipPojo);
        });

        // 用户插入成功，则插入用户的默认信息
        if (auroraDefaultUserInfoProperties != null) {

            if (StringUtils.hasLength(auroraDefaultUserInfoProperties.getSiteInfo())) {
                // 插入站点信息
                SiteSettingPojo siteSettingPojo = new SiteSettingPojo();
                siteSettingPojo.setUserUid(user.getUid());
                siteSettingPojo.setParamName(user.getUid() + "SiteInfo");
                siteSettingPojo.setParamValue(auroraDefaultUserInfoProperties.getSiteInfo().replaceAll("auUsernameua", user.getUsername()));
                siteSettingService.insertSiteSetting(siteSettingPojo);
            }

            if (StringUtils.hasLength(auroraDefaultUserInfoProperties.getNavbarInfo())) {
                SiteSettingPojo navbarInfoPojo = new SiteSettingPojo();
                navbarInfoPojo.setUserUid(user.getUid());
                navbarInfoPojo.setParamName(user.getUid() + "NavbarInfo");
                navbarInfoPojo.setParamValue(auroraDefaultUserInfoProperties.getNavbarInfo().replaceAll("auUserUidua", user.getUid() + ""));
                siteSettingService.insertSiteSetting(navbarInfoPojo);
            }

            if (StringUtils.hasLength(auroraDefaultUserInfoProperties.getPageInfo())) {
                SiteSettingPojo pagePojo = new SiteSettingPojo();
                pagePojo.setUserUid(user.getUid());
                pagePojo.setParamName(user.getUid() + "AllPageInfo");
                pagePojo.setParamValue(auroraDefaultUserInfoProperties.getPageInfo().replaceAll("auUserUidua", user.getUid() + ""));
                siteSettingService.insertSiteSetting(pagePojo);
            }

            // 插入欢迎文章
            if (StringUtils.hasLength(auroraDefaultUserInfoProperties.getWelcomeArticle())) {
                ArticlePojo articlePojo = new ArticlePojo();
                articlePojo.setUserUid(user.getUid());
                articlePojo.setDelete(false);
                articlePojo.setPublish(true);
                articlePojo.setOriginalArticle(true);
                // articlePojo.setCategoryNames("欢迎页");
                articlePojo.setTitle("HI " + user.getUsername());
                articlePojo.setContent(auroraDefaultUserInfoProperties.getWelcomeArticle());
                articleFeignService.insertArticle(articlePojo);
            }
        }
    }

    @Transactional
    public int updateUser(UserPojo user) throws UserException {
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
        int updateNum = auroraUserService.updateById(BeanUtils.copyProperties(user, User.class));
        if (updateNum == 1) {
            removeUserDetailsFromRedis(username);
        }
        return updateNum;
    }

    /**
     * 更新密码，忘记密码也会进入此流程，如果是更新密码，那么username,originPwd,newPwd是必须的，如果是忘记密码，需要username，secretKey
     * @return
     */
    public int updatePassword(UserPojo userPojo) {
        String username = userPojo.getUsername();
        String originPwd = userPojo.getOriginPwd();
        String newPwd = userPojo.getNewPwd();
        AssertUtils.stateThrow(StringUtils.hasLength(username), () -> new UserException("用户名不能为空"));
        // 查询此用户的原始密码
        User user = queryByUsernameContainPassword(username);
        AssertUtils.stateThrow(user != null, () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        // 查看密码是否匹配
        boolean matches = passwordEncoder.matches(originPwd, user.getPassword());
        AssertUtils.stateThrow(matches, () -> new UserException("密码错误"));

        // 修改密码
        user.setPassword(passwordEncoder.encode(newPwd));
        int updateNum = auroraUserService.updateById(user);
        if (updateNum == 1) {
            removeUserDetailsFromRedis(user.getUsername());
        }
        return updateNum;
    }

    /**
     * 忘记密码，先验证此用户名是否存在，如果存在，远程调用，查看此用户是否绑定了email，如果没有email，则不支持找回，如果存在，则发送重置密码
     * 的html到该email中
     * @param userPojo
     * @return
     */
    public int forgotPassword(UserPojo userPojo) {

        return 0;
    }

    @Transactional
    public Integer physicalDeleteUser(long uid) {
        return auroraUserService.deleteById(uid);
    }

    public Integer queryTotalUserCount(UserPojo user) {
        return auroraUserService.countByWhere(BeanUtils.copyProperties(user, User.class));
    }

    public Integer logicDeleteUser(long uid) {
        UserPojo pojo = new UserPojo();
        pojo.setDelete(true);
        pojo.setUid(uid);
        return updateUser(pojo);
    }

    public PageData<UserVO> queryAllByCondition(Condition<Long> condition) {
        return PageUtils.copyPageDataResult(auroraUserService.queryListByCondition(condition), UserVO.class);
    }

    public UserVO queryUserByUid(long uid) {
        return BeanUtils.copyProperties(auroraUserService.queryById(uid), UserVO.class);
    }

    public User queryByUsernameContainPassword(String username) {
        return auroraUserService.queryOne(new User(){{
            setUsername(username);
        }});
    }

    public UserVO queryOneData(UserPojo pojo) {
        return BeanUtils.copyProperties(auroraUserService.queryOne(BeanUtils.copyProperties(pojo, User.class)), UserVO.class);
    }

    public User queryByUidContainPassword(long uid) {
        return auroraUserService.queryById(uid);
    }

    public UserVO queryUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return BeanUtils.copyProperties(auroraUserService.queryOne(user), UserVO.class);
    }

    /**
     * 绑定邮箱，必须得登录之后才能绑定
     * @param pojo
     * @return
     * @throws BindException
     * @throws EmailException
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public int bindingEmail(UserPojo pojo) throws BindException, EmailException {
        String email = pojo.getEmailNumber();
        Long userUid = pojo.getUid();
        JwtUserInfo currentUser = UserUtils.getCurrentUser();
        AssertUtils.stateThrow(currentUser != null, () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_LOGIN));
        // 当前绑定邮箱的用户的uid必须和登录的用户uid一样
        AssertUtils.stateThrow(currentUser.getUserUid().equals(pojo.getUid()), () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_ILLEGAL_ACCESS));
        AssertUtils.stateThrow(StringUtils.hasLength(email), () -> new EmailException(ResponseStatusCodeEnum.PARAM_IS_INVALID));
        // 远程调用aurora-message服务，判断此email的uid是否存在
        EmailPojo emailPojo = new EmailPojo();
        emailPojo.setEmail(email);
        emailPojo.setUserUid(userUid);
        R r = emailFeignService.queryByEmailNumber(emailPojo);
        EmailVO queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r), "data", EmailVO.class);

        // AssertUtils.ifNullThrow(queriedEmailInfo,
        //         () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS));
        // AssertUtils.ifNullThrow(queriedEmailInfo.getEmail(),
        //         () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS));
        // 如果此邮箱号不存在则添加
        if (queriedEmailInfo == null || queriedEmailInfo.getUid() == null) {
            R insertEmailR = emailFeignService.insertEmail(emailPojo);
            if (insertEmailR.getSuccess()) {
                // 插入成功
                R r1 = emailFeignService.queryByEmailNumber(emailPojo);
                queriedEmailInfo = JSONUtils.parseObjFromResult(ConvertObjectUtils.jsonToString(r1), "data", EmailVO.class);
            }else {
                throw new EmailException("添加邮箱失败");
            }
        }
        AssertUtils.ifNullThrow(queriedEmailInfo, () -> new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_FAIL_BINDING));
        if (queriedEmailInfo.getUserUid() != null && !Objects.equals(queriedEmailInfo.getUserUid(), userUid)) {
            // 查询此用户是否绑定
            UserVO otherUserInfo = queryUserByUid(queriedEmailInfo.getUserUid());
            if (otherUserInfo != null && otherUserInfo.getVerifyEmail()) {
                throw new EmailException("此邮箱号属于另一用户");
            }
        }
        UserPojo userPojo = new UserPojo();
        userPojo.setEmailUid(queriedEmailInfo.getUid());
        userPojo.setUid(userUid);
        // 判断该用户是否绑定
        UserVO userVO = queryUserByUid(userPojo.getUid());
        // 如果已经绑定，那么则换绑
        userVO.setEmailUid(queriedEmailInfo.getUid());
        userVO.setVerifyEmail(false);
        // 判断该用户是否被删除
        AssertUtils.stateThrow(!userVO.getDelete(), () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_DELETE));

        int updateUserNum = updateUser(userPojo);
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
        return !auroraUserService.queryListByCondition(condition).getResult().isEmpty();
    }

    private String getUsername(Long userUid) {
        UserVO userVO = queryUserByUid(userUid);
        return userVO == null ? "" : userVO.getUsername();
    }

    private void setUserProperties(UserPojo user) {
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
        long time = new Date().getTime();
        if (auroraAccountProperties.getMailVerifyAccountExpirationTime() != null) {
            time = time + auroraAccountProperties.getMailVerifyAccountExpirationTime();
        }else {
            // TODO 系统并没有设置默认失效时间，则设置10分钟
            time = time + (10 * 60 * 1000);
        }
        String expirationTimeStr = DateUtils.format(new Date(time));
        EmailVerifyAccountDTO verifyAccountInfo = EmailVerifyAccountDTO.builder()
                .userUid(userVO.getUid())
                .expirationTime(auroraAccountProperties.getMailVerifyAccountExpirationTime())
                .verifyAccountUrl(verifyAccountUrl)
                .expirationTimeStr(expirationTimeStr)
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
