package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.exception.user.UserException;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.service.EmailService;

import javax.validation.groups.Default;
import java.math.BigInteger;
import java.util.List;

/**
 * 操作au_email表的controller
 * @author qsyyke
 */

@Api(tags = "邮箱相关的操作")
@RestController
@RequestMapping("/message/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @ApiOperation(value = "向数据库中插入新的邮箱记录，比如主机，授权码等")
    @ResponseResult
    @PostMapping("")
    public ModifyResult insertEmail(@Validated({Insert.class,Default.class}) EmailDO email) throws EmailException, BindException, UserException, InstantiationException, IllegalAccessException {
        return emailService.insertEmail(email);
    }

    @ApiOperation(value = "根据唯一uid删除某条邮箱",notes = "uid为long型")
    @ResponseResult
    @DeleteMapping("/{uid}")
    public ModifyResult deleteEmailByUid(@PathVariable(value = "uid") long uid) {
        return emailService.deleteEmailByUid(uid);
    }

    @ApiOperation(value = "修改邮箱记录的删除状态",notes = "必须传入uid和delete字段")
    @ResponseResult
    @DeleteMapping("/deleteStatus")
    public ModifyResult updateDeleteStatus(@Validated({Update.class}) EmailDO email) {
        return emailService.updateDeleteStatus(email);
    }

    @ApiOperation(value = "根据emailDO实体，更新邮箱记录",notes = "必须传入uid以及delete字段")
    @ResponseResult
    @PutMapping("")
    public ModifyResult updateEmailByUid(@Validated({Update.class, Default.class}) EmailDO email) {
        return emailService.updateEmail(email);
    }

    @ApiOperation(value = "根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合",notes = "部分字段使用了模糊查询")
    @ResponseResult
    @GetMapping("")
    public List<EmailDO> queryAll(@RequestParam(required = false) EmailDO email, PaginationDTO pagination) {
        if (email == null) {
            email = EmailDO.builder().build();
        }
        return emailService.queryAllEmail(email,pagination);
    }

    @ApiOperation(value = "根据uid查询")
    @ResponseResult
    @GetMapping("/{uid}")
    public EmailDO queryByUid(@PathVariable("uid") long uid) {
        return emailService.queryByUid(uid);
    }

    @ApiOperation(value = "根据userUid进行查询")
    @ResponseResult
    @GetMapping("/userUid/{userUid}")
    public EmailDO queryByUserUid(@PathVariable("userUid") long userUid) {
        return emailService.queryByUserUid(userUid);
    }

    @ApiOperation(value = "根据邮箱号进行查询")
    @ResponseResult
    @GetMapping("/email/{email}")
    public EmailDO queryByEmail(@PathVariable("email") String email) {
        return emailService.queryByEmail(email);
    }
}
