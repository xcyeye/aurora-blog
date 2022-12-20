package xyz.xcye.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.auth.service.LoginInfoService;
import xyz.xcye.auth.vo.LoginInfoVO;
import xyz.xcye.core.annotaion.FieldFilter;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 23:27
 */

@Tag(name = "登录日志")
@RequestMapping("/login/loginInfo")
@RestController
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @ModifyOperation
    @Operation(summary = "根据uid删除登录日志")
    @PostMapping("/physicalDeleteLoginInfo")
    public int physicalDeleteLoginInfo(@RequestBody Long uid) {
        return loginInfoService.physicalDeleteLoginInfo(uid);
    }

    @ModifyOperation
    @Operation(summary = "根据uid，批量删除")
    @PostMapping("/batchDeleteLoginInfoByUid")
    public int batchDeleteLoginInfoByUid(@RequestBody Long[] uids) {
        return loginInfoService.batchDeleteLoginInfoByUid(uids);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"","",""},
            ignoreRole = {OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME})
    @SelectOperation
    @Operation(summary = "根据条件查询")
    @PostMapping("/queryListLoginInfoByCondition")
    public PageData<LoginInfoVO> queryListLoginInfoByCondition(@RequestBody Condition<Long> condition) {
        return loginInfoService.queryListLoginInfoByCondition(condition);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"loginIp","loginLocation","username"})
    @SelectOperation
    @Operation(summary = "根据用户名查询")
    @PostMapping("/queryLoginInfoByUsername")
    public LoginInfoVO queryLoginInfoByUsername(@RequestBody String username) {
        return loginInfoService.queryLoginInfoByUsername(username);
    }
}
