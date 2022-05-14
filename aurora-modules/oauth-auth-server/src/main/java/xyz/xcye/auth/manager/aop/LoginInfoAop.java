package xyz.xcye.auth.manager.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.AuthRedisConstant;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.properties.SecurityProperties;
import xyz.xcye.auth.service.LoginInfoService;
import xyz.xcye.core.enums.RegexEnum;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.NetWorkUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 * @date Created in 2022/5/14 09:01
 */

@Component
@Aspect
public class LoginInfoAop {

    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    private AuroraProperties auroraProperties;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 记录用户的登录信息，将登录情况记录到数据库中
     * @param point
     * @return
     * @throws Throwable
     */
    @Before("execution(public * xyz.xcye.auth.service.JwtTokenUserDetailsService.loadUserByUsername(..))")
    public void logProcessRequestTime(JoinPoint point) {

        // 开始记录 获取当前请求对象
        HttpServletRequest request = AuroraRequestUtils.getCurrentRequest();

        // 获取用户名
        Object[] args = point.getArgs();
        String username = "";
        if (args.length > 0) {
            username = args[0].toString();
        }

        // 判断用户名是否规范
        legalUsernname(username);

        // 生成唯一uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId());

        // 将此唯一uid和用户名保存在请求头中
        request.setAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_UID_NAME, uid);
        request.setAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_USERNAME_NAME, username);

        // 判断该用户是否达到最大登录失败次数
        isReachesMaxFailureNum(username);

        // 组装对象
        LoginInfo loginInfo = LoginInfo.builder().
                username(username)
                .loginIp(NetWorkUtils.getIpAddr(request))
                .operationSystemInfo(NetWorkUtils.getOperationInfo(request))
                .loginLocation("保山")
                .uid(uid)
                .build();

        // 查看用户上次
        int insertNum = loginInfoService.insertSelective(loginInfo);

        // 插入失败，退出
        if (insertNum != 1) {
            throw new UsernameNotFoundException("系统异常");
        }
    }

    @Before("execution(public * xyz.xcye.auth.handler.OauthServerAuthenticationFailureHandler.onAuthenticationFailure(..))")
    public void loginFailure(JoinPoint point) {
        // 登录失败，获取失败信息，更新
        Object[] args = point.getArgs();

        // 获取失败消息
        String errorMessage = "";
        for (Object arg : args) {
            if (arg instanceof AuthenticationException) {
                AuthenticationException exception = (AuthenticationException) arg;
                errorMessage = exception.getMessage();
            }
        }

        // request中没有用户名，不做任何处理
        if (getUsernameFromRequest() == null) {
            return;
        }

        // 处理登录失败结果
        handlerLoginResult(errorMessage, false);
        storageLoginSituationToRedis(false);
    }

    @Before("execution(public * xyz.xcye.auth.handler.OauthServerAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    public void loginSuccess(JoinPoint point) {
        // 登录失败，获取失败信息，更新
        handlerLoginResult("登录成功", true);
        storageLoginSituationToRedis(true);
    }

    /**
     * 判断该用户的登录失败次数是否达到最大值，如果达到最大致，则锁住该用户的信息
     * @param username
     */
    private void isReachesMaxFailureNum(String username) {
        Integer loginFailureNum = (Integer) redisTemplate.opsForValue()
                .get(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);

        if (loginFailureNum == null) {
            // 首次登录
            return;
        }

        if (loginFailureNum >= securityProperties.getMaxLoginFailure()) {
            // 登录失败次数达到最大值，AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX时间段为不允许登录
            Long expire = redisTemplate.getExpire(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
            if (expire == null || -1 == expire) {
                expire = Long.parseLong((securityProperties.getReLoginMinute() * 60) + "");
                // 设置该用户的失败次数的ttl
                redisTemplate.expire(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username, Duration.ofSeconds(expire));
            }

            // 计算剩余重新登录时间
            String reLoginTime = DateUtils.format(new Date(System.currentTimeMillis() + (expire * 1000)));
            throw new UsernameNotFoundException("登录失败次数达到最大值,下次登录时间 " + reLoginTime);
        }

        // 没有达到最大值，不做处理
    }

    /**
     * 把该用户名的登录成功/失败情况存入redis中
     * @param loginStatus
     */
    private void storageLoginSituationToRedis(boolean loginStatus) {
        // 获取用户名
        String username = getUsernameFromRequest();

        // 如果用户登录成功，如果redis中存在在登录成功之前的失败记录，则删除，如果登录失败，则调整用户的登录失败次数加1
        if (loginStatus) {
            // 删除redis中的登录情况
            Boolean delete = redisTemplate.delete(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
            if (Boolean.FALSE.equals(delete)) {
                int i = 0;
                while (i < securityProperties.getRedisDeleteRetry()) {
                    Boolean retryDelete = redisTemplate.delete(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
                    if (Boolean.TRUE.equals(retryDelete)) {
                        break;
                    }
                    i++;
                }
            }
            return;
        }

        // 登录失败，向redis中插入此用户的登录失败次数 没有超过最大值才添加
        Integer loginFailureNum = (Integer) redisTemplate.opsForValue().get(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
        if (loginFailureNum == null || loginFailureNum < securityProperties.getMaxLoginFailure()) {
            redisTemplate.opsForValue().increment(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
        }
    }

    /**
     * 处理登录的结果，也就是把该用户的登录成功，失败情况存入mysql中
     * @param message
     * @param status
     */
    private void handlerLoginResult(String message, boolean status) {
        // 获取唯一uid
        Long uid = getUidFromRequest();
        String username = getUsernameFromRequest();

        // 只有用户的登录失败次数没有达到最大值的时候才修改用户登录记录
        Integer loginFailureNum = (Integer) redisTemplate.opsForValue().get(AuthRedisConstant.USER_LOGIN_FAILURE_NUMBER_PREFIX + username);
        if (loginFailureNum != null && loginFailureNum >= securityProperties.getMaxLoginFailure()) {
            return;
        }

        // 修改登录信息
        LoginInfo loginInfo = LoginInfo.builder()
                .uid(uid)
                .status(status)
                .updateTime(DateUtils.format())
                .build();
        if (StringUtils.hasLength(message)) {
            loginInfo.setMessage(message);
        }
        loginInfoService.updateByPrimaryKeySelective(loginInfo);
    }

    /**
     * 判断用户名是否合法
     * @param username
     */
    private void legalUsernname(String username) {
        // 判断用户名是否规范
        boolean matches = Pattern.matches(RegexEnum.USERNAME_REGEX.getRegex(), username);
        if (!matches) {
            throw new UsernameNotFoundException("用户名不正确");
        }
    }

    private String getUsernameFromRequest() {
        HttpServletRequest request = AuroraRequestUtils.getCurrentRequest();
        return (String) request.getAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_USERNAME_NAME);
    }

    private Long getUidFromRequest() {
        HttpServletRequest request = AuroraRequestUtils.getCurrentRequest();
        // 获取唯一uid
        return (Long) request.getAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_UID_NAME);
    }
}
