package xyz.xcye.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.message.pojo.SendMailPojo;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;
import java.io.IOException;

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
    public void sendSimpleMail(@RequestBody SendMailPojo pojo) throws MessagingException {
        sendMailService.sendSimpleMail(pojo);
    }

    @Operation(summary = "发送自定义html")
    @ModifyOperation
    @PostMapping("/customMail")
    public void sendCustomMail(@RequestBody SendMailPojo pojo) throws MessagingException, IOException {
       sendMailService.sendCustomMail(pojo);
    }

    @Operation(summary = "重新发送自定义邮件")
    @ModifyOperation
    @PostMapping("/resendCustomMail")
    public void resendCustomMail(@RequestBody SendMailPojo pojo) throws MessagingException, IOException {
        sendMailService.resendCustomMail(pojo);
    }
}
