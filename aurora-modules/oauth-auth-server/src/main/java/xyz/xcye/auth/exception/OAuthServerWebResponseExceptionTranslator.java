package xyz.xcye.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import xyz.xcye.aurora.util.AuroraSpringUtils;
import xyz.xcye.auth.manager.aop.LoginInfoAop;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.core.exception.login.LoginException;

/**
 * 自定义异常翻译器，针对用户名、密码异常，授权类型不支持的异常进行处理
 * 这个类，是认证失败的处理类，也就是申请token，密码等错误时，进行处理
 * @author qsyyke
 * @date Created in 2022/5/4 13:03
 */


public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private LoginInfoAop loginInfoAop;

    /**
     * 业务处理方法，重写这个方法返回客户端信息
     */
    @Override
    public ResponseEntity<R> translate(Exception e){
        loginInfoAop = AuroraSpringUtils.getBean(LoginInfoAop.class);
        loginInfoAop.authFailure(e);
        return new ResponseEntity<>(doTranslateHandler(e.getCause()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 根据异常定制返回信息
     */
    private R doTranslateHandler(Throwable e) {
        //初始值，系统错误，判断异常，不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            return getCodeEnum(ResponseStatusCodeEnum.OAUTH_NOT_SUPPORT_AUTH_TYPE);
            //用户名或密码异常
        }else if (e instanceof LoginException) {
            return getCodeEnum(e.getMessage());
        } else if(e instanceof InvalidGrantException){
            return getCodeEnum(e.getMessage());
        } else if (e instanceof UsernameNotFoundException) {
            return getCodeEnum(e.getMessage());
        } else if (e instanceof AuthenticationException) {
            return getCodeEnum(ResponseStatusCodeEnum.SYSTEM_ERROR);
        } else if (e instanceof AuroraException) {
            AuroraException auroraException = (AuroraException) e;
            return R.failure(auroraException.statusCode, auroraException.getMessage());
        }
        return getCodeEnum(ResponseStatusCodeEnum.UNKNOWN);
    }

    private R getCodeEnum(ResponseStatusCodeEnum codeEnum) {
        return R.failure(codeEnum.getCode(), codeEnum.getMessage());
    }

    private R getCodeEnum(String message) {
        return R.failure(ResponseStatusCodeEnum.UNKNOWN.getCode(), message);
    }
}
