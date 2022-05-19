package xyz.xcye.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.enums.RegexEnum;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.core.util.ConvertObjectUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.core.util.lambda.AssertUtils;
import xyz.xcye.mail.api.feign.FileFeignService;
import xyz.xcye.message.enums.MailTemplateEnum;
import xyz.xcye.message.mail.SendMailRealize;
import xyz.xcye.message.po.EmailLog;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.util.MailTemplateUtils;
import xyz.xcye.message.util.ParseEmailTemplate;
import xyz.xcye.message.vo.EmailVO;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private SendMailRealize sendMailRealize;

    @Autowired
    private EmailLogService emailLogService;

    private final String mailSubject = "Aurora-你有新的邮件";

    /**
     * 配置文件中的发送者的邮箱号
     */
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private EmailService emailService;
    @Autowired
    private FileFeignService fileFeignService;

    @Override
    public int sendHtmlMail(StorageSendMailInfo storageSendMailInfo)
            throws MessagingException, IOException, EmailException {

        AssertUtils.stateThrow(storageSendMailInfo.getUserUid() != null,
                () -> new UserException(ResponseStatusCodeEnum.PERMISSION_USER_NOT_EXIST));
        String keyName = storageSendMailInfo.getSendType().name();

        // 1. 根据userUid+SendHtmlMailTypeNameEnum到文件服务中，查找此模板在本地存储的位置
        // 查询文件的时候，keyword就是filename
        /*String templateFileName = storageSendMailInfo.getUserUid() + storageSendMailInfo.getSendType().name() + ".html";
        Condition<Long> condition = Condition.instant(templateFileName);
        R r = fileFeignService.queryAllFile(condition);
        FileVO fileVO = JSONUtils.parseObjFromResult(r, "data", FileVO.class);*/

        // 如果storageSendMailInfo中的receiverEmail为null或者没有长度的话，那么便更具userUid从数据库中查找
        // 如果数据库中都没有的话，那么抛出异常
        setReceiverEmail(storageSendMailInfo);

        // 如果是发送自定义html的话
        if (storageSendMailInfo.getSendType() == SendHtmlMailTypeNameEnum.CUSTOM_HTML) {
            return sendEmail(storageSendMailInfo.getSubject(), storageSendMailInfo.getHtmlContent(),
                    storageSendMailInfo.getReceiverEmail());
        }

        String templateContent = null;
        try {
            templateContent = MailTemplateUtils.readContentFromTemplateFile(storageSendMailInfo.getSendType().name(), "mailTemplate");
        } catch (IOException e) {
            // 如果发生异常，也就是没有模板，那么则直接发送对象的toString字符
            templateContent = storageSendMailInfo.getReplacedMap().toString();
        }

        /*templateContent = Optional.ofNullable(templateContent)
                .orElse(MailTemplateUtils.readContentFromFile(fileVO.getStoragePath()));*/

        // 判断传入的subject是否为null或者空
        String subject = getRightSubject(storageSendMailInfo.getSubject(), mailSubject);

        //解析邮件发送内容
        String sendContent = null;
        try {
            sendContent = ParseEmailTemplate.parseHtmlMailTemplate(storageSendMailInfo.getReplacedMap(), templateContent);
        } catch (Exception e) {
            // 如果解析html失败，则直接发送json数据
            sendContent = ConvertObjectUtils.jsonToString(storageSendMailInfo.getReplacedMap());
        }

        return sendEmail(subject, sendContent, storageSendMailInfo.getReceiverEmail());
    }

    @Override
    public int sendSimpleMail(String to, String subject, String content) {
        // 设置标志点
        boolean sendFlag = false;
        try {
            sendMailRealize.sendSimpleMail(to, subject, content);
            sendFlag = true;
        } catch (MailException e) {
            LogUtils.logExceptionInfo(e);
        }

        EmailLog emailLog = new EmailLog(null, subject, content, to, senderEmail,sendFlag,
                DateUtils.format(new Date()),"");
        return emailLogService.insertEmailLog(emailLog);
    }

    @Override
    public int sendCustomMail(String receiverEmail, String subject, String content) throws MessagingException {
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

        if (customSubject.length() > FieldLengthConstant.EMAIL_SUBJECT) {
            return customSubject.substring(0,FieldLengthConstant.EMAIL_SUBJECT);
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
    private int sendEmail(String subject,String sendContent,String receiverEmail) throws MessagingException {
        // 设置标志点
        boolean sendFlag = false;
        //发送邮件
        try {
            sendMailRealize.sendHtmlMail(receiverEmail, subject, sendContent);
            sendFlag = true;
        } catch (MessagingException e) {
            LogUtils.logExceptionInfo(e);
        }

        //如果运行到这里，说说邮件发送成功 向数据库中插入数据
        EmailLog emailLog = new EmailLog(null,subject,sendContent,receiverEmail,
                senderEmail, sendFlag,DateUtils.format(new Date()),"");
        return emailLogService.insertEmailLog(emailLog);
    }

    private MailTemplateEnum getDefaultTemplate(SendHtmlMailTypeNameEnum mailKeyNameEnum) throws IOException {
        switch (mailKeyNameEnum) {
            case RECEIVE_COMMENT:
                // 使用默认的收到评论html
                return MailTemplateEnum.RECEIVE_COMMENT;
            case REPLY_COMMENT:
                // 使用默认的回复评论html
                return MailTemplateEnum.REPLY_COMMENT;
            case COMMON_NOTICE:
                // 使用默认的通知模板
                return MailTemplateEnum.COMMON_NOTICE;
            case VERIFY_ACCOUNT:
                // 使用默认的邮箱验证账户html
                return MailTemplateEnum.VERIFY_ACCOUNT;
            default:
                return MailTemplateEnum.DEFAULT;
        }
    }

    /**
     * 为storageSendMailInfo对象中的收件人邮箱设置一个有效合法的邮箱号
     * 发送邮件的邮箱号优先级 直接存在receiverEmail>通过userUid查询到的email
     * @param storageSendMailInfo
     * @return
     * @throws ReflectiveOperationException
     * @throws EmailException
     */
    private void setReceiverEmail(StorageSendMailInfo storageSendMailInfo)
            throws EmailException {
        if (!StringUtils.hasLength(storageSendMailInfo.getReceiverEmail())) {
            EmailVO emailVO = emailService.queryByUserUid(storageSendMailInfo.getUserUid());
            if (emailVO == null || !StringUtils.hasLength(emailVO.getEmail())) {
                throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_NOT_EXISTS);
            }

            // 数据库中，存在，则直接进行设置
            storageSendMailInfo.setReceiverEmail(emailVO.getEmail());
        }

        // 验证邮箱号是否正确
        if(!Pattern.matches(RegexEnum.MAIL_REGEX.getRegex(), storageSendMailInfo.getReceiverEmail())) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_MISTAKE);
        }
    }

    private Comment getRepliedComment(StorageSendMailInfo storageSendMailInfo) {
        if (storageSendMailInfo.getSendType() != SendHtmlMailTypeNameEnum.REPLY_COMMENT) {
            return null;
        }

        // 是回复评论，则组装一个新的数据，用于发送收到评论的邮件
        Map<String, Object> additionalData = storageSendMailInfo.getAdditionalData();
        if (additionalData == null) {
            // 可能会存在用户消息乱发送请求，则直接跳过
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(ConvertObjectUtils.jsonToString(additionalData));
        return JSON.parseObject(jsonObject.getString(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.name()), Comment.class);
    }
}
