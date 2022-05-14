package xyz.xcye.auth.manager.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.aurora.properties.AuroraProperties;
import xyz.xcye.aurora.util.AuroraRequestUtils;
import xyz.xcye.auth.constant.RequestConstant;
import xyz.xcye.auth.po.LoginInfo;
import xyz.xcye.auth.service.LoginInfoService;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.NetWorkUtils;
import xyz.xcye.core.util.id.GenerateInfoUtils;

import javax.servlet.http.HttpServletRequest;

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

        // 生成唯一uid
        long uid = GenerateInfoUtils.generateUid(auroraProperties.getSnowFlakeWorkerId(),
                auroraProperties.getSnowFlakeDatacenterId());

        // 将此唯一uid保存在请求头中
        request.setAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_UID_NAME, uid);

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

        // 修改
        handlerLoginResult(errorMessage, false);
    }

    @Before("execution(public * xyz.xcye.auth.handler.OauthServerAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    public void loginSuccess(JoinPoint point) {
        // 登录失败，获取失败信息，更新
        handlerLoginResult("登录成功", true);
    }

    private void handlerLoginResult(String message, boolean status) {
        HttpServletRequest request = AuroraRequestUtils.getCurrentRequest();
        // 获取唯一uid
        Long uid = (Long) request.getAttribute(RequestConstant.AUTH_SERVER_STORAGE_LOGIN_UID_NAME);

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
}
