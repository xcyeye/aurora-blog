package xyz.xcye.message.service;

import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.message.pojo.SendMailPojo;

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
    void sendHtmlMail(StorageSendMailInfo storageSendMailInfo)
            throws MessagingException, IOException, EmailException;

    /**
     * 发送简单的邮件
     * @param pojo 接收者邮箱
     * @return
     * @throws MessagingException
     */
    void sendSimpleMail(SendMailPojo pojo) throws MessagingException;

    /**
     * 发送自定义html邮件
     * @param pojo 接收者邮箱号
     * @return
     * @throws MessagingException
     */
    void sendCustomMail(SendMailPojo pojo) throws MessagingException, IOException;

    /**
     * 重新发送自定义的邮件
     * @param pojo
     * @return
     */
    void resendCustomMail(SendMailPojo pojo) throws MessagingException, IOException;
}