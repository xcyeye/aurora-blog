package xyz.xcye.auth.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

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
    public ResponseEntity<String> translate(Exception e){
        String resultMsg = doTranslateHandler(e);
        return new ResponseEntity<>(resultMsg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 根据异常定制返回信息
     * TODO 自己根据业务封装
     */
    private String doTranslateHandler(Exception e) {
        //初始值，系统错误，
        //判断异常，不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            return "不支持的认证方式";
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            return "用户名或者密码错误";
        }
        return "其他错误";
    }
}
