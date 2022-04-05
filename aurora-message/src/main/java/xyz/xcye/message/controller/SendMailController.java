package xyz.xcye.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.email.EmailCommonNotice;
import xyz.xcye.common.entity.email.EmailVerifyAccount;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Comment;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;
import java.math.BigInteger;

/**
 * 使用发送请求的方式发送邮件
 * @author qsyyke
 */

@RequestMapping("/message/mail")
@RestController
public class SendMailController {

    @Autowired
    private SendMailService sendMailService;

    @ResponseResult
    @PostMapping("/notice")
    public ModifyResult sendCommonNotice(@RequestParam(value = "userUid") BigInteger userUid,
                                         EmailCommonNotice emailCommonNotice,
                                         @RequestParam(value = "subject",required = false) String subject) throws MessagingException {
        return sendMailService.sendCommonNoticeMail(emailCommonNotice,userUid,subject);
    }

    @ResponseResult
    @PostMapping("/replyComment")
    public ModifyResult sendReplyCommentMail(Comment replyingCommentInfo, Comment repliedCommentInfo,
                                             @RequestParam(value = "userUid") BigInteger userUid,
                                             @RequestParam(value = "subject",required = false) String subject) throws MessagingException {
        return sendMailService.sendReplyCommentMail(replyingCommentInfo,repliedCommentInfo,userUid,subject);
    }

    @ResponseResult
    @PostMapping("/receiveComment")
    public ModifyResult sendReceiveCommentMail(Comment receiveCommentInfo,
                                               @RequestParam(value = "userUid") BigInteger userUid,
                                               @RequestParam(value = "subject",required = false) String subject) throws MessagingException {
        return sendMailService.sendReceiveCommentMail(receiveCommentInfo,userUid,subject);
    }

    /**
     * 邮件验证url的模板解析
     * @param verifyAccount
     * @return 邮件发送的content，html
     */
    @ResponseResult
    @PostMapping("/verifyAccount")
    public ModifyResult sendVerifyAccountMail(EmailVerifyAccount verifyAccount,
                                              @RequestParam(value = "userUid") BigInteger userUid,
                                              @RequestParam(value = "subject",required = false) String subject) throws MessagingException {
        return sendMailService.sendVerifyAccountMail(verifyAccount,userUid,subject);
    }

    /**
     * 发送简单的邮件
     * @return
     */
    public ModifyResult sendSimpleMail( @RequestParam(value = "receiverEmail") String receiverEmail,
                                        @RequestParam(value = "subject") String subject,
                                        @RequestParam(value = "content") String content) throws MessagingException {
        return sendMailService.sendSimpleMail(receiverEmail,subject,content);
    }
}
