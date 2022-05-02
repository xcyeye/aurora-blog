package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.service.UserService;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.common.vo.UserVO;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author qsyyke
 */

@Slf4j
@RequestMapping("/admin/user")
@RestController
@Api(tags = "用户相关写操作")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    @ResponseResult
    @ApiOperation(value = "添加新用户")
    public ModifyResult insertUser(@Validated({Insert.class, Default.class})UserDO userDO,
                                   @Validated({Insert.class, Default.class}) UserAccountDO userAccountDO)
            throws ReflectiveOperationException, UserException {
        return userService.insertUser(userDO,userAccountDO);
    }

    @PutMapping("")
    @ResponseResult
    @ApiOperation(value = "修改用户信息")
    public ModifyResult updateUser(@Validated({Update.class, Default.class})UserDO userDO) throws UserException {
        return userService.updateUser(userDO);
    }

    @DeleteMapping("/{uid}")
    @ResponseResult
    @ApiOperation(value = "删除用户信息")
    public ModifyResult deleteUserByUid(@PathVariable("uid") long uid) {
        return userService.deleteByUid(uid);
    }

    @GetMapping("/{uid}")
    @ResponseResult
    @ApiOperation(value = "通过uid查询用户信息")
    public UserVO queryUserByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return userService.queryByUid(uid);
    }

    @GetMapping("")
    @ResponseResult
    @ApiOperation(value = "查询所有满足要求的用户信息")
    public List<UserVO> insertUser(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        return userService.queryAllByCondition(condition);
    }

    @ApiOperation("绑定邮箱")
    @ResponseResult
    @PutMapping("/bindingEmail/{emailUid}")
    public ModifyResult bindingEmail(@PathVariable("emailUid") long emailUid)
            throws BindException, EmailException, ReflectiveOperationException {
        return userService.bindingEmail(emailUid);
    }
}
