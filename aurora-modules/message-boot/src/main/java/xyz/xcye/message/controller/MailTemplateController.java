package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.message.po.MailTemplate;
import xyz.xcye.message.service.MailTemplateService;
import xyz.xcye.message.vo.MailTemplateVO;

import javax.validation.groups.Default;

/**
 * 操作邮件发送模板的controller
 * @author qsyyke
 */

@Api(tags = "操作邮件发送模板的controller")
@RequestMapping("/message/mailTemplate")
@RestController
public class MailTemplateController {

    @Autowired
    private MailTemplateService  mailTemplateService;

    @ModifyOperation
    @PostMapping
    @ApiOperation("插入新的邮件模板")
    public int insertMailTemplate(@Validated({Insert.class, Default.class}) MailTemplate mailTemplate) {
        return mailTemplateService.insertMailTemplate(mailTemplate);
    }

    @ModifyOperation
    @DeleteMapping("/{uid}")
    @ApiOperation("根据uid删除邮件模板")
    public int deleteMailTemplateByUid(@PathVariable("uid") long uid) {
        return mailTemplateService.deleteMailTemplate(uid);
    }

    @ModifyOperation
    @PutMapping
    @ApiOperation("更新邮件发送模板")
    public int updateMailTemplate(@Validated({Update.class, Default.class}) MailTemplate mailTemplate) {
        return mailTemplateService.updateMailTemplate(mailTemplate);
    }

    @SelectOperation
    @GetMapping
    @ApiOperation("查询所有邮件模板")
    public PageData<MailTemplateVO> queryAllMailTemplate(Condition<Long> condition) {
        return mailTemplateService.queryAllMailTemplate(condition);
    }

    @SelectOperation
    @GetMapping("/{uid}")
    @ApiOperation("根据uid查询邮件模板")
    public MailTemplateVO queryMailTemplateByUid(@PathVariable("uid") long uid) {
        return mailTemplateService.queryMailTemplateByUid(uid);
    }

    @SelectOperation
    @GetMapping("/userUid/{userUid}")
    @ApiOperation("根据userUid查询邮件模板")
    public MailTemplateVO queryMailTemplateByUserUid(@PathVariable("userUid") long userUid) {
        return mailTemplateService.queryMailTemplateByUserUid(userUid);
    }
}
