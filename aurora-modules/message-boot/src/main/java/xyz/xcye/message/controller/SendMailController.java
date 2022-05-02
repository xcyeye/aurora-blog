package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.core.annotaion.ResponseResult;
import xyz.xcye.core.entity.result.ModifyResult;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;

/**
 * 使用发送请求的方式发送邮件
 * @author qsyyke
 */

@Api(value = "发送邮件相关接口",tags = "发送邮件相关接口")
@RequestMapping("/message/sendMail")
@RestController
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    /**
     * 发送简单的邮件
     * @return
     */
    @ResponseResult
    @ApiOperation(value = "发送普通文本")
    @PostMapping("/simpleText")
    public ModifyResult sendSimpleMail( @RequestParam(value = "receiverEmail") String receiverEmail,
                                        @RequestParam(value = "subject") String subject,
                                        @RequestParam(value = "content") String content) throws MessagingException {
        return sendMailService.sendSimpleMail(receiverEmail,subject,content);
    }

    @ApiOperation(value = "发送自定义html")
    @ResponseResult
    @PostMapping("/customMail")
    public ModifyResult sendCustomMail(@RequestParam("subject") String subject,
                                       @RequestParam("content") String content,
                                       @RequestParam("receiverEmail") String receiverEmail) throws MessagingException {
        return sendMailService.sendCustomMail(receiverEmail,subject,content);
    }
}
