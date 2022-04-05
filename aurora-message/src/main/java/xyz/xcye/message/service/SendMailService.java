package xyz.xcye.message.service;

import org.springframework.mail.MailException;
import xyz.xcye.common.entity.email.EmailCommonNotice;
import xyz.xcye.common.entity.email.EmailVerifyAccount;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.Comment;
import xyz.xcye.common.entity.table.Email;
import xyz.xcye.common.entity.table.EmailLog;
import xyz.xcye.message.enums.EmailTemplateReplaceRegex;

import javax.mail.MessagingException;
import java.math.BigInteger;

/**
 * 邮件发送服务
 * @author qsyyke
 */

public interface SendMailService {
    /**
     * 根据数据库中某个用户的邮件普通通知模板，把特定内容替换成emailCommonNotice中所存放的内容
     * @param emailCommonNotice
     * @return 解析之后的邮件发送内容，包含html，content
     */
    ModifyResult sendCommonNoticeMail(EmailCommonNotice emailCommonNotice, BigInteger userUid,String subject) throws MessagingException;

    /**
     * 如果有用户回复某条评论，则会调用该解析方法进行解析，模板存放与数据库中
     * @param replyingCommentInfo
     * @param repliedCommentInfo
     * @return 邮件发送的content，包含html，content
     */
    ModifyResult sendReplyCommentMail(Comment replyingCommentInfo, Comment repliedCommentInfo, BigInteger userUid,String subject) throws MessagingException;

    /**
     * 如果有用户发布了评论，那么就使用该方法进行模板内容的替换
     * @param receiveCommentInfo
     * @return 邮件发送的内容，包含html，content
     */
    ModifyResult sendReceiveCommentMail(Comment receiveCommentInfo,BigInteger userUid,String subject) throws MessagingException;

    /**
     * 邮件验证url的模板解析
     * @param verifyAccount
     * @return 邮件发送的content，html
     */
    ModifyResult sendVerifyAccountMail(EmailVerifyAccount verifyAccount, BigInteger userUid,String subject) throws MessagingException;

    /**
     * 发送简单的邮件
     * @return
     */
    ModifyResult sendSimpleMail(String to,String subject, String content) throws MessagingException;
}