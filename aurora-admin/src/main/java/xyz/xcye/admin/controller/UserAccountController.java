package xyz.xcye.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.service.UserAccountService;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.UserAccountDO;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.UserAccountVO;

import javax.validation.groups.Default;
import java.util.List;

/**
 * 操作userAccount的controller，只提供查询，修改操作，其余的通过用户控制
 * @author qsyyke
 */

@RequestMapping("/admin/account")
@RestController
@Api(tags = "操作用户账户信息相关的接口,只提供查询，修改操作，其余的通过用户控制")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @ResponseResult
    @PutMapping
    @ApiOperation("修改账户权限信息")
    public ModifyResult updateUserAccount(@Validated({Update.class, Default.class}) UserAccountDO userAccountDO) {
        return userAccountService.update(userAccountDO);
    }

    @ApiOperation("查询所有用户权限信息")
    @GetMapping
    @ResponseResult
    public List<UserAccountVO> queryAllByCondition(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        return userAccountService.queryAllByCondition(condition);
    }

    @GetMapping("/{uid}")
    @ResponseResult
    @ApiOperation("通过uid查询")
    public UserAccountVO queryByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return userAccountService.queryByUid(uid);
    }

    @GetMapping("/userUid/{userUid}")
    @ResponseResult
    @ApiOperation("通过userUid查询")
    public UserAccountVO queryByUserUid(@PathVariable("userUid") long userUid) throws ReflectiveOperationException {
        return userAccountService.queryByUserUid(userUid);
    }
}
