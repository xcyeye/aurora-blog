package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.admin.vo.UserVO;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@RequestMapping("/admin/user")
@RestController
@RefreshScope
@Tag(name = "用户相关写操作")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insertUser")
    @ModifyOperation
    @Operation(summary = "添加新用户")
    public void insertUser(@Validated({Insert.class, Default.class}) @RequestBody UserPojo user) throws UserException {
        userService.insertUser(user);
    }

    @PostMapping("/updateUser")
    @ModifyOperation
    @Operation(summary = "修改用户信息")
    public Integer updateUser(@Validated({Update.class, Default.class}) @RequestBody UserPojo user) throws UserException {
        return userService.updateUser(user);
    }

    @Operation(summary = "忘记密码")
    @PostMapping("/forgotPassword")
    @ModifyOperation
    public Integer forgotPassword(@RequestBody UserPojo user) {
        return userService.forgotPassword(user);
    }

    @Operation(summary = "更新密码")
    @PostMapping("/updatePassword")
    @ModifyOperation
    public Integer updatePassword(@RequestBody UserPojo user) {
        return userService.updatePassword(user);
    }

    @PostMapping("/logicDeleteUser")
    @ModifyOperation
    @Operation(summary = "逻辑删除用户信息")
    public Integer logicDeleteUser(@RequestBody UserPojo user) {
        return userService.logicDeleteUser(user.getUid());
    }

    @PostMapping("/physicalDeleteUser")
    @ModifyOperation
    @Operation(summary = "真正的从数据库中删除用户信息")
    public Integer physicalDeleteUser(@RequestBody UserPojo user) {
        return userService.physicalDeleteUser(user.getUid());
    }

    @PostMapping("/queryTotalUserCount")
    @ModifyOperation
    @Operation(summary = "查询用户数")
    public Integer queryTotalUserCount(@RequestBody UserPojo user) {
        return userService.queryTotalUserCount(user);
    }

    @PostMapping("/queryUserByUid")
    @SelectOperation
    @Operation(summary = "通过uid查询用户信息")
    public UserVO queryUserByUid(@RequestBody UserPojo user) {
        return userService.queryUserByUid(user.getUid());
    }

    @PostMapping("/queryUserByUsername")
    @SelectOperation
    @Operation(summary = "通过username查询用户信息")
    public UserVO queryUserByUsername(@RequestBody UserPojo user) {
        return userService.queryUserByUsername(user.getUsername());
    }

    @PostMapping(value = "/queryUserByUsernameContainPassword", headers = "content-type=application/json;charset=UTF-8;")
    @SelectOperation
    @Operation(summary = "通过username查询用户信息")
    public User queryUserByUsernameContainPassword(@RequestBody UserPojo user) {
        return userService.queryByUsernameContainPassword(user.getUsername());
    }

    @PostMapping(value = "/queryOneData", headers = "content-type=application/json;charset=UTF-8;")
    @SelectOperation
    @Operation(summary = "通过username查询用户信息")
    public User queryOneData(@RequestBody UserPojo user) {
        return userService.queryOneData(user);
    }

    @PostMapping("/queryListUserByCondition")
    @SelectOperation
    @Operation(summary = "查询所有满足要求的用户信息")
    public PageData<UserVO> queryListUserByCondition(@RequestBody Condition<Long> condition) {
        return userService.queryAllByCondition(condition);
    }

    @Operation(summary = "绑定邮箱")
    @ModifyOperation
    @PostMapping("/bindingEmail")
    public int bindingEmail(@RequestBody UserPojo pojo) throws BindException, EmailException {
        return userService.bindingEmail(pojo);
    }
}
