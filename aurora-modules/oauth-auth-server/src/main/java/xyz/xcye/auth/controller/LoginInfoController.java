package xyz.xcye.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.auth.constant.OauthJwtConstant;
import xyz.xcye.auth.service.LoginInfoService;
import xyz.xcye.auth.vo.LoginInfoVO;
import xyz.xcye.core.annotaion.FieldFilter;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.oauth.api.service.UserFeignService;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 23:27
 */

@Api(tags = "登录日志")
@RequestMapping("/login/loginInfo")
@RestController
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @ModifyOperation
    @ApiOperation("根据uid删除登录日志")
    @DeleteMapping("/{uid}")
    public int deleteByPrimaryKey(@PathVariable("uid") Long uid) {
        return loginInfoService.deleteByPrimaryKey(uid);
    }

    @ModifyOperation
    @ApiOperation("根据uid，批量删除")
    @DeleteMapping
    public int deleteByUidBatch(Long[] uids) {
        return loginInfoService.deleteByUidBatch(uids);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"","",""},
            ignoreRole = {OauthJwtConstant.SUPER_ADMINISTRATOR_ROLE_NAME})
    @SelectOperation
    @ApiOperation("根据条件查询")
    @GetMapping
    public PageData<LoginInfoVO> selectByCondition(Condition<Long> condition) {
        return loginInfoService.selectByCondition(condition);
    }

    @FieldFilter(value = LoginInfoVO.class, excludeFields = {"loginIp","loginLocation","username"})
    @SelectOperation
    @ApiOperation("根据用户名查询")
    @GetMapping("/{username}")
    public LoginInfoVO selectByUsername(@PathVariable("username") String username) {
        return loginInfoService.selectByUsername(username);
    }
}
