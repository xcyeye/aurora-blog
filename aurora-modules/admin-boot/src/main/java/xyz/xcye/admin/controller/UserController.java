package xyz.xcye.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
@Tag(name = "用户相关写操作")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ModifyOperation
    @Operation(summary = "添加新用户")
    public void insertUser(@Validated({Insert.class, Default.class}) UserPojo user) throws UserException {
        userService.insertUserSelective(user);
    }

    @PutMapping("")
    @ModifyOperation
    @Operation(summary = "修改用户信息")
    public void updateUser(@Validated({Update.class, Default.class})UserPojo user) throws UserException {
        userService.updateUserSelective(user);
    }

    @Operation(summary = "更新密码")
    @PutMapping("/pwd")
    @ModifyOperation
    public void updatePassword(String username, String originPwd, String newPwd) {
        userService.updatePassword(username, originPwd, newPwd);
    }

    @DeleteMapping("/{uid}")
    @ModifyOperation
    @Operation(summary = "逻辑删除用户信息")
    public void logicDeleteUserByUid(@PathVariable("uid") long uid) {
        userService.logicDeleteByUid(uid);
    }

    @DeleteMapping("/delete/{uid}")
    @ModifyOperation
    @Operation(summary = "真正的从数据库中删除用户信息")
    public void realDeleteUserByUid(@PathVariable("uid") long uid) {
        userService.realDeleteByUid(uid);
    }

    @GetMapping("/userUid/{uid}")
    @SelectOperation
    @Operation(summary = "通过uid查询用户信息")
    public UserVO queryUserByUid(@PathVariable("uid") long uid) {
        return userService.queryUserByUid(uid);
    }

    @GetMapping("/username/{username}")
    @SelectOperation
    @Operation(summary = "通过username查询用户信息")
    public UserVO queryUserByUsername(@PathVariable("username") String username) {
        return userService.queryUserByUsername(username);
    }

    @PostMapping("/pwd/{username}")
    @SelectOperation
    @Operation(summary = "通过username查询用户信息")
    public User queryUserByUsernameContainPassword(@PathVariable("username") String username) {
        return userService.queryByUsernameContainPassword(username);
    }

    @GetMapping("")
    @SelectOperation
    @Operation(summary = "查询所有满足要求的用户信息")
    public PageData<UserVO> insertUser(Condition<Long> condition) {
        return userService.queryAllByCondition(condition);
    }

    @Operation(summary = "绑定邮箱")
    @ModifyOperation
    @PutMapping("/bindingEmail/{email}")
    public int bindingEmail(@PathVariable("email") String email) throws BindException, EmailException {
        return userService.bindingEmail(email);
    }
}
