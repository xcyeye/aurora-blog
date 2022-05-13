package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.User;
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
@Api(tags = "用户相关写操作")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ModifyOperation
    @ApiOperation(value = "添加新用户")
    public int insertUser(@Validated({Insert.class, Default.class}) User user)
            throws UserException {
        return userService.insertUser(user);
    }

    @PutMapping("")
    @ModifyOperation
    @ApiOperation(value = "修改用户信息")
    public int updateUser(@Validated({Update.class, Default.class})User user) throws UserException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{uid}")
    @ModifyOperation
    @ApiOperation(value = "逻辑删除用户信息")
    public int logicDeleteUserByUid(@PathVariable("uid") long uid) {
        return userService.logicDeleteByUid(uid);
    }

    @DeleteMapping("/delete/{uid}")
    @ModifyOperation
    @ApiOperation(value = "真正的从数据库中删除用户信息")
    public int realDeleteUserByUid(@PathVariable("uid") long uid) {
        return userService.realDeleteByUid(uid);
    }

    @GetMapping("/userUid/{uid}")
    @SelectOperation
    @ApiOperation(value = "通过uid查询用户信息")
    public UserVO queryUserByUid(@PathVariable("uid") long uid) {
        return userService.queryByUid(uid);
    }

    @GetMapping("/username/{username}")
    @SelectOperation
    @ApiOperation(value = "通过username查询用户信息")
    public UserVO queryUserByUsername(@PathVariable("username") String username) {
        return userService.queryByUsername(username);
    }

    @PostMapping("/pwd/{username}")
    @SelectOperation
    @ApiOperation(value = "通过username查询用户信息")
    public User queryUserByUsernameContainPassword(@PathVariable("username") String username) {
        return userService.queryByUsernameContainPassword(username);
    }

    @GetMapping("")
    @SelectOperation
    @ApiOperation(value = "查询所有满足要求的用户信息")
    public PageData<UserVO> insertUser(Condition<Long> condition) {
        return userService.queryAllByCondition(condition);
    }

    @ApiOperation("绑定邮箱")
    @ModifyOperation
    @PutMapping("/bindingEmail/{emailUid}")
    public int bindingEmail(@PathVariable("emailUid") long emailUid)
            throws BindException, EmailException {
        return userService.bindingEmail(emailUid);
    }
}
