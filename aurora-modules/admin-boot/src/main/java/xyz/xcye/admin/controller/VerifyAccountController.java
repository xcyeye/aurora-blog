package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.service.verify.CommonVerifyUrlService;
import xyz.xcye.core.annotaion.controller.ResponseRealResult;
import xyz.xcye.core.exception.user.UserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 用户验证账户信息的相关操作
 * @author qsyyke
 */

@Api(tags = "用户验证账户信息的相关操作")
@RestController
@RequestMapping("/admin/verifyAccount")
public class VerifyAccountController {

    @Autowired
    private CommonVerifyUrlService commonVerifyUrlService;

    @ResponseRealResult
    @GetMapping("/bindEmail/{incomingSecretKey}")
    public String emailVerifyAccount(@PathVariable("incomingSecretKey") String incomingSecretKey,
                                     HttpServletRequest request, HttpServletResponse response) throws UserException, IOException {
        boolean bindEmail = commonVerifyUrlService.bindEmail(incomingSecretKey);

        if (bindEmail) {
            return  "绑定成功";
        }
        return "绑定失败";
    }
}
