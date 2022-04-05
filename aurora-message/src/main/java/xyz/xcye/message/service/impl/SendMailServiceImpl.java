package xyz.xcye.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.common.entity.email.EmailCommonNotice;
import xyz.xcye.common.entity.email.EmailVerifyAccount;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Comment;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.entity.table.EmailLog;
import xyz.xcye.common.util.DateUtil;
import xyz.xcye.message.mail.SendMailRealize;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.util.ParseEmailTemplate;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author qsyyke
 */

@Component
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SendMailRealize sendMailRealize;

    @Autowired
    private EmailLogService emailLogService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public ModifyResult sendCommonNoticeMail(EmailCommonNotice emailCommonNotice, BigInteger userUid ,String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        Email queryEmail = new Email();
        queryEmail.setUserUid(userUid);
        emailService.queryAllEmail(queryEmail,null);
        Email email = emailService.queryByUid(userUid + "");

        //如果不存在，则直接返回null
        if (email == null) {
            return new ModifyResult(0,false,"此" + userUid + "并不存在对应的email记录",null);
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,email.getNoticeSubject());

        //设置时间
        emailCommonNotice.setNoticeTime(DateUtil.format(new Date()));

        //获取解析之后的待发送内容
        String sendContent = ParseEmailTemplate.sendCommonNoticeMail(emailCommonNotice, email);

        return sendEmail(subject,sendContent,emailCommonNotice.getReceiverEmail());
    }

    @Override
    public ModifyResult sendReplyCommentMail(Comment replyingCommentInfo, Comment repliedCommentInfo, BigInteger userUid,String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        Email email = emailService.queryByUid(userUid + "");

        //如果不存在，则直接返回null
        if (email == null) {
            return new ModifyResult(0,false,"此" + userUid + "并不存在对应的email记录",null);
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,email.getReplyCommentSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendReplyCommentMail(replyingCommentInfo, repliedCommentInfo, email);

        return sendEmail(subject,sendContent,repliedCommentInfo.getEmail());
    }

    @Override
    public ModifyResult sendReceiveCommentMail(Comment receiveCommentInfo, BigInteger userUid,String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        Email email = emailService.queryByUid(userUid + "");

        //如果不存在，则直接返回null
        if (email == null) {
            return new ModifyResult(0,false,"此" + userUid + "并不存在对应的email记录",null);
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,email.getReceiveCommentSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendReceiveCommentMail(receiveCommentInfo,email);

        return sendEmail(subject,sendContent,email.getEmail());
    }

    @Override
    public ModifyResult sendVerifyAccountMail(EmailVerifyAccount verifyAccount, BigInteger userUid,String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        Email email = emailService.queryByUid(userUid + "");

        //如果不存在，则直接返回null
        if (email == null) {
            return new ModifyResult(0,false,"此" + userUid + "并不存在对应的email记录",null);
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,email.getVerifyAccountSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendVerifyAccountMail(verifyAccount,email);

        return sendEmail(subject,sendContent,email.getEmail());
    }

    @Override
    public ModifyResult sendSimpleMail(String to,String subject, String content) {
        sendMailRealize.sendSimpleMail(to,subject,content);

        EmailLog emailLog = new EmailLog(null, subject, content, to, senderEmail,
                DateUtil.format(new Date()), true);
        return emailLogService.insertEmailLog(emailLog);
    }

    /**
     * 判断自定义的subject是否为空或者null，如果为null或者空，返回数据库中的subject
     * @param customSubject
     * @param dbSubject
     * @return
     */
    private String getRightSubject(String customSubject,String dbSubject) {
        if (StringUtils.hasLength(customSubject)) {
            return customSubject;
        }
        return dbSubject;
    }

    private ModifyResult sendEmail(String subject,String sendContent,String receiverEmail) throws MessagingException {
        //发送邮件
        sendMailRealize.sendHtmlMail(receiverEmail,subject,sendContent);

        //如果运行到这里，说说邮件发送成功 向数据库中插入数据
        EmailLog emailLog = new EmailLog(null,subject,sendContent,receiverEmail,
                senderEmail,DateUtil.format(new Date()),true);
        return emailLogService.insertEmailLog(emailLog);
    }
}
