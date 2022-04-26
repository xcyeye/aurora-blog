package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.dto.EmailCommonNoticeDTO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.service.SendMailService;

import javax.mail.MessagingException;
import javax.validation.groups.Default;
import java.io.IOException;

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

    @ApiOperation(value = "发送普通通知")
    @ResponseResult
    @PostMapping("/notice")
    public ModifyResult sendCommonNotice(@RequestParam(value = "userUid") long userUid,
                                         @Validated({Insert.class, Default.class}) EmailCommonNoticeDTO emailCommonNotice,
                                         @RequestParam(value = "subject",required = false) String subject)
            throws MessagingException, IOException, ReflectiveOperationException {
        return sendMailService.sendCommonNoticeMail(emailCommonNotice,userUid,subject);
    }

    @ApiOperation(value = "发送回复评论")
    @ResponseResult
    @PostMapping("/replyComment")
    public ModifyResult sendReplyCommentMail(@RequestParam("replying") CommentDO replyingCommentInfo,
                                             @RequestParam("replied") CommentDO repliedCommentInfo,
                                             @RequestParam(value = "userUid") long userUid,
                                             @RequestParam(value = "subject",required = false) String subject)
            throws MessagingException, BindException, IOException, ReflectiveOperationException {
        return sendMailService.sendReplyCommentMail(replyingCommentInfo,repliedCommentInfo,userUid,subject);
    }

    @ApiOperation(value = "发送收到评论")
    @ResponseResult
    @PostMapping("/receiveComment")
    public ModifyResult sendReceiveCommentMail(CommentDO receiveCommentInfo,
                                               @RequestParam(value = "userUid") long userUid,
                                               @RequestParam(value = "subject",required = false) String subject)
            throws MessagingException, BindException, IOException, ReflectiveOperationException {
        return sendMailService.sendReceiveCommentMail(receiveCommentInfo,userUid,subject);
    }

    /**
     * 邮件验证url的模板解析
     * @param verifyAccount
     * @return 邮件发送的content，html
     */
    @ApiOperation(value = "发送验证账户")
    @ResponseResult
    @PostMapping("/verifyAccount")
    public ModifyResult sendVerifyAccountMail(EmailVerifyAccountDTO verifyAccount,
                                              @RequestParam(value = "userUid") long userUid,
                                              @RequestParam(value = "subject",required = false) String subject)
            throws MessagingException, IOException, BindException, ReflectiveOperationException {
        return sendMailService.sendVerifyAccountMail(verifyAccount,userUid,subject);
    }

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
