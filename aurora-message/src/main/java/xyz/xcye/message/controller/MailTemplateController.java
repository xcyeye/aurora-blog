package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.MailTemplateDO;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.vo.MailTemplateVO;
import xyz.xcye.message.service.MailTemplateService;

import javax.validation.groups.Default;
import java.util.List;

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

    @ResponseResult
    @PostMapping
    @ApiOperation("插入新的邮件模板")
    public ModifyResult insertMailTemplate(@Validated({Insert.class, Default.class})MailTemplateDO mailTemplate) {
        return mailTemplateService.insertMailTemplate(mailTemplate);
    }

    @ResponseResult
    @DeleteMapping("/{uid}")
    @ApiOperation("根据uid删除邮件模板")
    public ModifyResult deleteMailTemplateByUid(@PathVariable("uid") long uid) {
        return mailTemplateService.deleteMailTemplate(uid);
    }

    @ResponseResult
    @PutMapping
    @ApiOperation("更新邮件发送模板")
    public ModifyResult updateMailTemplate(@Validated({Update.class, Default.class}) MailTemplateDO mailTemplate) {
        return mailTemplateService.updateMailTemplate(mailTemplate);
    }

    @ResponseResult
    @GetMapping
    @ApiOperation("查询所有邮件模板")
    public List<MailTemplateVO> queryAllMailTemplate(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        return mailTemplateService.queryAllMailTemplate(condition);
    }

    @ResponseResult
    @GetMapping("/{uid}")
    @ApiOperation("根据uid查询邮件模板")
    public MailTemplateVO queryMailTemplateByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return mailTemplateService.queryMailTemplateByUid(uid);
    }

    @ResponseResult
    @GetMapping("/userUid/{userUid}")
    @ApiOperation("根据userUid查询邮件模板")
    public MailTemplateVO queryMailTemplateByUserUid(@PathVariable("userUid") long userUid) throws ReflectiveOperationException {
        return mailTemplateService.queryMailTemplateByUserUid(userUid);
    }
}
