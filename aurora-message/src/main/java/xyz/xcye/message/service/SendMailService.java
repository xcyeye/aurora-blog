package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.dto.EmailCommonNoticeDTO;
import xyz.xcye.common.dto.EmailVerifyAccountDTO;
import xyz.xcye.common.entity.result.ModifyResult;

import javax.mail.MessagingException;
import java.math.BigInteger;

/**
 * 邮件发送服务
 * @author qsyyke
 */

public interface SendMailService {

    /**
     * 根据数据库中某个用户的邮件普通通知模板，把特定内容替换成emailCommonNotice中所存放的内容
     * @param emailCommonNotice 需要发送的普通通知的数据
     * @param userUid 使用哪个用户的模板
     * @param subject 标题
     * @return
     * @throws MessagingException
     */
    ModifyResult sendCommonNoticeMail(EmailCommonNoticeDTO emailCommonNotice, long userUid, String subject) throws MessagingException;

    /**
     * 如果有用户回复某条评论，则会调用该解析方法进行解析，模板存放与数据库中
     * @param replyingCommentInfo 某个用户正在回复的评论数据
     * @param repliedCommentInfo 被回复的评论数据
     * @param userUid 使用哪个用户的模板
     * @param subject 标题
     * @return
     * @throws MessagingException
     * @throws BindException
     */
    ModifyResult sendReplyCommentMail(CommentDO replyingCommentInfo, CommentDO repliedCommentInfo, long userUid, String subject) throws MessagingException, BindException;

    /**
     * 如果有用户发布了评论，那么就使用该方法进行模板内容的替换
     * @param receiveCommentInfo 收到的评论数据对象
     * @param userUid 使用哪个用户的模板
     * @param subject 发送标题
     * @return
     * @throws MessagingException
     * @throws BindException
     */
    ModifyResult sendReceiveCommentMail(CommentDO receiveCommentInfo,long userUid,String subject) throws MessagingException, BindException;

    /**
     * 发送验证账户的邮件
     * @param verifyAccount 需要验证的账户相关信息
     * @param userUid 使用哪个用户的模板
     * @param subject 标题
     * @return
     * @throws MessagingException 发送失败
     */
    ModifyResult sendVerifyAccountMail(EmailVerifyAccountDTO verifyAccount, long userUid, String subject) throws MessagingException;

    /**
     * 发送简单的邮件
     * @param to 接收者邮箱
     * @param subject 标题
     * @param content 简单文本内容
     * @return
     * @throws MessagingException
     */
    ModifyResult sendSimpleMail(String to,String subject, String content) throws MessagingException;

    /**
     * 发送自定义html邮件
     * @param receiverEmail 接收者邮箱号
     * @param subject 标题
     * @param content 发送的内容，如果发送的是html，必须传入解析之后的html内容
     * @return
     * @throws MessagingException
     */
    ModifyResult sendCustomMail(String receiverEmail,String subject,String content) throws MessagingException;
}