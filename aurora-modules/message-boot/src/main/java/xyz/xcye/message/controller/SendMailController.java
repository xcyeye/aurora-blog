package xyz.xcye.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;

/**
 * 使用发送请求的方式发送邮件
 * @author qsyyke
 */

@Tag(name = "发送邮件相关接口")
@RequestMapping("/message/sendMail")
@RestController
@RefreshScope
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    /**
     * 发送简单的邮件
     * @return
     */
    @ModifyOperation
    @Operation(summary = "发送普通文本")
    @PostMapping("/simpleText")
    public int sendSimpleMail( @RequestParam(value = "receiverEmail") String receiverEmail,
                                        @RequestParam(value = "subject") String subject,
                                        @RequestParam(value = "content") String content) throws MessagingException {
        return sendMailService.sendSimpleMail(receiverEmail,subject,content);
    }

    @Operation(summary = "发送自定义html")
    @ModifyOperation
    @PostMapping("/customMail")
    public int sendCustomMail(@RequestParam("subject") String subject,
                                       @RequestParam("content") String content,
                                       @RequestParam("receiverEmail") String receiverEmail) throws MessagingException {
        return sendMailService.sendCustomMail(receiverEmail,subject,content);
    }

    @Operation(summary = "重新发送自定义邮件")
    @ModifyOperation
    @PostMapping("/resend/{uid}")
    public int resendCustomMail(@PathVariable("uid") Long emailLogUid) throws MessagingException {
        return sendMailService.resendCustomMail(emailLogUid);
    }
}
