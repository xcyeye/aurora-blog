package xyz.xcye.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dos.EmailLogDO;
import xyz.xcye.common.dto.EmailCommonNoticeDTO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.message.mail.SendMailRealize;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.util.ParseEmailTemplate;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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

    /**
     * 配置文件中的发送者的邮箱号
     */
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * 邮件的最大标题长度
     */
    @Value("${aurora.mail.max-subject-length}")
    private int maxSubjectLength;

    @Override
    public ModifyResult sendCommonNoticeMail(EmailCommonNoticeDTO emailCommonNotice, long userUid , String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        EmailDO emailDO = emailService.queryByUserUid(userUid);
        if (emailDO == null) {
            return ModifyResult.operateResult(userUid + "邮件" + ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getMessage(),
                    0,ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getCode());
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,emailDO.getNoticeSubject());

        //设置时间
        emailCommonNotice.setNoticeTime(DateUtils.format(new Date()));

        //获取解析之后的待发送内容
        String sendContent = ParseEmailTemplate.sendCommonNoticeMail(emailCommonNotice, emailDO);

        return sendEmail(subject,sendContent,emailCommonNotice.getReceiverEmail());
    }

    @Override
    public ModifyResult sendReplyCommentMail(CommentDO replyingCommentInfo, CommentDO repliedCommentInfo, long userUid, String subject) throws MessagingException, BindException {
        //根据userUid获取对应的Email对象
        EmailDO emailDO = emailService.queryByUserUid(userUid);
        if (emailDO == null) {
            return ModifyResult.operateResult(userUid + "邮件" + ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getMessage(),
                    0,ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getCode());
        }

        //判断传入的subject是否为null或者空或者长度超过限制
        subject = getRightSubject(subject,emailDO.getReplyCommentSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendReplyCommentMail(replyingCommentInfo, repliedCommentInfo, emailDO);

        // 用户回复评论，除了被回复的评论的用户会收到提醒，发布评论所对应的页面(文章)的作者也会收到
        sendReceiveCommentMail(replyingCommentInfo,userUid,"");

        return sendEmail(subject,sendContent,repliedCommentInfo.getEmail());
    }

    @Override
    public ModifyResult sendReceiveCommentMail(CommentDO receiveCommentInfo, long userUid,String subject) throws MessagingException, BindException {
        //根据userUid获取对应的Email对象
        EmailDO emailDO = emailService.queryByUserUid(userUid);
        if (emailDO == null) {
            return ModifyResult.operateResult(userUid + "邮件" + ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getMessage(),
                    0,ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getCode());
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,emailDO.getReceiveCommentSubject());

        ValidationUtils.valid(receiveCommentInfo, Insert.class);

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendReceiveCommentMail(receiveCommentInfo,emailDO);

        return sendEmail(subject,sendContent,emailDO.getEmail());
    }

    @Override
    public ModifyResult sendVerifyAccountMail(EmailVerifyAccountDTO verifyAccount, long userUid, String subject) throws MessagingException {
        //根据userUid获取对应的Email对象
        EmailDO emailDO = emailService.queryByUserUid(userUid);
        if (emailDO == null) {
            return ModifyResult.operateResult(userUid + "邮件" + ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getMessage(),
                    0,ResponseStatusCodeEnum.COMMON_RECORD_NOT_EXISTS.getCode());
        }

        //判断传入的subject是否为null或者空
        subject = getRightSubject(subject,emailDO.getVerifyAccountSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.sendVerifyAccountMail(verifyAccount,emailDO);

        return sendEmail(subject,sendContent,emailDO.getEmail());
    }

    @Override
    public ModifyResult sendSimpleMail(String to,String subject, String content) {
        // 设置标志点
        boolean sendFlag = false;
        try {
            sendMailRealize.sendSimpleMail(to, subject, content);
            sendFlag = true;
        } catch (MailException e) {
            e.printStackTrace();
        }

        EmailLogDO emailLog = new EmailLogDO(null, subject, content, to, senderEmail,sendFlag,
                DateUtils.format(new Date()),"");
        return emailLogService.insertEmailLog(emailLog);
    }

    @Override
    public ModifyResult sendCustomMail(String receiverEmail, String subject, String content) throws MessagingException {
        return sendEmail(subject, content, receiverEmail);
    }

    /**
     * 判断自定义的subject是否为空或者null，如果为null或者空，返回数据库中的subject
     * @param customSubject
     * @param dbSubject
     * @return
     */
    private String getRightSubject(String customSubject,String dbSubject) {
        if (!StringUtils.hasLength(customSubject)) {
            return dbSubject;
        }

        if (customSubject.length() > maxSubjectLength) {
            return customSubject.substring(0,maxSubjectLength);
        }
        return customSubject;
    }

    /**
     * 发送html邮件
     * @param subject 最终发送的邮件标题
     * @param sendContent 最终经过解析之后邮件内容(html)
     * @param receiverEmail 接收者的邮箱号
     * @return
     * @throws MessagingException
     */
    private ModifyResult sendEmail(String subject,String sendContent,String receiverEmail) throws MessagingException {
        // 设置标志点
        boolean sendFlag = false;
        //发送邮件
        try {
            sendMailRealize.sendHtmlMail(receiverEmail, subject, sendContent);
            sendFlag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //如果运行到这里，说说邮件发送成功 向数据库中插入数据
        EmailLogDO emailLog = new EmailLogDO(null,subject,sendContent,receiverEmail,
                senderEmail, sendFlag,DateUtils.format(new Date()),"");
        return emailLogService.insertEmailLog(emailLog);
    }

    private List<EmailDO> getEmailsByUserUid(long userUid) {
        EmailDO queryEmail = new EmailDO();
        queryEmail.setUserUid(userUid);
        return emailService.queryAllEmail(queryEmail, null);
    }
}
