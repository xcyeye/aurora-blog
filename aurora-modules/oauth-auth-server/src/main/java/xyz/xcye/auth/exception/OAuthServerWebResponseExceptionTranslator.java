package xyz.xcye.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

/**
 * 自定义异常翻译器，针对用户名、密码异常，授权类型不支持的异常进行处理
 * @author qsyyke
 * @date Created in 2022/5/4 13:03
 */


public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    /**
     * 业务处理方法，重写这个方法返回客户端信息
     */
    @Override
    public ResponseEntity<R> translate(Exception e){
        return new ResponseEntity<>(doTranslateHandler(e), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 根据异常定制返回信息
     */
    private R doTranslateHandler(Exception e) {
        //初始值，系统错误，判断异常，不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            return getCodeEnum(ResponseStatusCodeEnum.OAUTH_NOT_SUPPORT_AUTH_TYPE);
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            return getCodeEnum(ResponseStatusCodeEnum.PERMISSION_USER_MISTAKE);
        }else if (e instanceof UsernameNotFoundException) {
            return getCodeEnum(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST);
        }
        return getCodeEnum(ResponseStatusCodeEnum.UNKNOWN);
    }

    private R getCodeEnum(ResponseStatusCodeEnum codeEnum) {
        return R.failure(codeEnum.getCode(), codeEnum.getMessage());
    }
}
