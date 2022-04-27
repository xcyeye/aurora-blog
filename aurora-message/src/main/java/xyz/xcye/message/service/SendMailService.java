package xyz.xcye.message.service;

import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * 邮件发送服务
 * @author qsyyke
 */

public interface SendMailService {

    /**
     * 发送html邮件
     * @param storageSendMailInfo 保存邮件发送的相关数据
     * @return
     * @throws MessagingException 发送失败
     */
    ModifyResult sendHtmlMail(StorageSendMailInfo storageSendMailInfo)
            throws MessagingException, IOException, ReflectiveOperationException;

    /**
     * 发送简单的邮件
     * @param receiverEmail 接收者邮箱
     * @param subject 标题
     * @param content 简单文本内容
     * @return
     * @throws MessagingException
     */
    ModifyResult sendSimpleMail(String receiverEmail,String subject, String content) throws MessagingException;

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