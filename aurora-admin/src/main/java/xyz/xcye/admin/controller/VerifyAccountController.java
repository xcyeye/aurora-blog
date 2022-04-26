package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xcye.admin.service.redis.UserRedisService;
import xyz.xcye.common.exception.user.UserException;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户验证账户信息的相关操作
 * @author qsyyke
 */

@Api(tags = "用户验证账户信息的相关操作")
@Controller
@RequestMapping("/admin/verifyAccount")
public class VerifyAccountController {

    @Autowired
    private UserRedisService userRedisService;

    @PostMapping("/")
    public String emailVerifyAccount(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     HttpServletRequest request) throws UserException, ReflectiveOperationException {
        boolean verifyAccountInfo = userRedisService.updateUserVerifyAccountInfo(username, password);
        request.setAttribute("username",username);

        if (verifyAccountInfo) {
            return "verifyAccountSuccess.html";
        }else {
            return "verifyAccountFail.html";
        }
    }

    @GetMapping("/login")
    public String emailVerifyAccountLogin() {
        return "verifyLogin.html";
    }
}
