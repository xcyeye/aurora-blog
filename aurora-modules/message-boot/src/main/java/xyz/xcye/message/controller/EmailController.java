package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.dto.Condition;
import xyz.xcye.core.annotaion.ResponseResult;
import xyz.xcye.core.entity.result.ModifyResult;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.message.po.Email;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.vo.EmailVO;

import javax.validation.groups.Default;
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
    public ModifyResult insertEmail(@Validated({Insert.class,Default.class}) Email email)
            throws BindException, ReflectiveOperationException, AuroraException {
        return emailService.insertEmail(email);
    }

    @ApiOperation(value = "根据唯一uid删除某条邮箱",notes = "uid为long型")
    @ResponseResult
    @DeleteMapping("/{uid}")
    public ModifyResult deleteEmailByUid(@PathVariable(value = "uid") long uid) {
        return emailService.deleteEmailByUid(uid);
    }

    @ApiOperation(value = "根据emailDO实体，更新邮箱记录",notes = "必须传入uid以及delete字段")
    @ResponseResult
    @PutMapping("")
    public ModifyResult updateEmailByUid(@Validated({Update.class, Default.class}) Email email) {
        return emailService.updateEmail(email);
    }

    @ApiOperation(value = "根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合",notes = "部分字段使用了模糊查询")
    @ResponseResult
    @GetMapping("")
    public List<EmailVO> queryAll(Condition<Long> condition) throws ReflectiveOperationException {
        return emailService.queryAllEmail(condition);
    }

    @ApiOperation(value = "根据uid查询")
    @ResponseResult
    @GetMapping("/{uid}")
    public EmailVO queryByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return emailService.queryByUid(uid);
    }

    @ApiOperation(value = "根据userUid进行查询")
    @ResponseResult
    @GetMapping("/userUid/{userUid}")
    public EmailVO queryByUserUid(@PathVariable("userUid") long userUid) throws ReflectiveOperationException {
        return emailService.queryByUserUid(userUid);
    }

    @ApiOperation(value = "根据邮箱号进行查询")
    @ResponseResult
    @GetMapping("/email/{email}")
    public EmailVO queryByEmail(@PathVariable("email") String email) throws ReflectiveOperationException {
        return emailService.queryByEmail(email);
    }
}
