package xyz.xcye.auth.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xyz.xcye.auth.util.OauthServerUtils;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qsyyke
 * @date Created in 2022/5/8 08:40
 */

@Component
public class OauthServerAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        OauthServerUtils.failure(request, response, exception.getMessage(),
                ResponseStatusCodeEnum.PERMISSION_USER_MISTAKE.getCode());
    }
}
