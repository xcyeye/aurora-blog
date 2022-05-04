package xyz.xcye.message.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.message.po.Email;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.vo.EmailVO;
import xyz.xcye.mybatis.entity.Condition;
import xyz.xcye.mybatis.entity.PageData;

import javax.validation.groups.Default;

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
    @ModifyOperation
    @PostMapping("")
    public int insertEmail(@Validated({Insert.class,Default.class}) Email email)
            throws BindException, AuroraException {
        return emailService.insertEmail(email);
    }

    @ApiOperation(value = "根据唯一uid删除某条邮箱",notes = "uid为long型")
    @ModifyOperation
    @DeleteMapping("/{uid}")
    public int deleteEmailByUid(@PathVariable(value = "uid") long uid) {
        return emailService.deleteEmailByUid(uid);
    }

    @ApiOperation(value = "根据emailDO实体，更新邮箱记录",notes = "必须传入uid以及delete字段")
    @ModifyOperation
    @PutMapping("")
    public int updateEmailByUid(@Validated({Update.class, Default.class}) Email email) {
        return emailService.updateEmail(email);
    }

    @ApiOperation(value = "根据EmailDO实体中的字段以及分页参数查询所有数据，返回一个集合",notes = "部分字段使用了模糊查询")
    @SelectOperation
    @GetMapping("")
    public PageData<EmailVO> queryAll(Condition<Long> condition) {
        return emailService.queryAllEmail(condition);
    }

    @ApiOperation(value = "根据uid查询")
    @SelectOperation
    @GetMapping("/{uid}")
    public EmailVO queryByUid(@PathVariable("uid") long uid) {
        return emailService.queryByUid(uid);
    }

    @ApiOperation(value = "根据userUid进行查询")
    @SelectOperation
    @GetMapping("/userUid/{userUid}")
    public EmailVO queryByUserUid(@PathVariable("userUid") long userUid) {
        return emailService.queryByUserUid(userUid);
    }

    @ApiOperation(value = "根据邮箱号进行查询")
    @SelectOperation
    @GetMapping("/email/{email}")
    public EmailVO queryByEmail(@PathVariable("email") String email) {
        return emailService.queryByEmail(email);
    }
}
