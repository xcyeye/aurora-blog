package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/email/{userUid}")
    public String emailVerifyAccount(@PathVariable("userUid") long userUid,
                                     HttpServletRequest request) throws UserException {
        boolean verifyAccountInfo = userRedisService.updateUserVerifyAccountInfo(userUid);
        request.setAttribute("username",userUid);

        if (verifyAccountInfo) {
            return "verifyAccountSuccess.html";
        }else {
            return "verifyAccountFail.html";
        }
    }
}
