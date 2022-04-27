package xyz.xcye.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.common.constant.FieldLengthConstant;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.entity.table.EmailLogDO;
import xyz.xcye.common.enums.RegexEnum;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.enums.SendHtmlMailKeyNameEnum;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.vo.EmailVO;
import xyz.xcye.common.vo.MailTemplateVO;
import xyz.xcye.message.enums.MailTemplateEnum;
import xyz.xcye.message.mail.SendMailRealize;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.service.EmailService;
import xyz.xcye.message.service.MailTemplateService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.util.MailTemplateUtils;
import xyz.xcye.message.util.ParseEmailTemplate;

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

    @Autowired
    private MailTemplateService mailTemplateService;

    /**
     * 配置文件中的发送者的邮箱号
     */
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private EmailService emailService;

    @Override
    public ModifyResult sendHtmlMail(StorageSendMailInfo storageSendMailInfo)
            throws MessagingException, IOException, ReflectiveOperationException, EmailException {
        String keyName = storageSendMailInfo.getSendType().getKeyName();

        // 1. 根据userUid和type从数据库中获取模板，如果没有的话，则使用默认的
        ConditionDTO<Long> condition = new ConditionDTO();
        condition.setOtherUid(storageSendMailInfo.getUserUid());
        condition.setKeyword(keyName);
        MailTemplateVO mailTemplateVO = BeanUtils.getSingleObjFromList(mailTemplateService.queryAllMailTemplate(condition), MailTemplateVO.class);

        /**
         * 如果storageSendMailInfo中的receiverEmail为null或者没有长度的话，那么便更具userUid从数据库中查找
         * 如果数据库中都没有的话，那么抛出异常
         */
        storageSendMailInfo = setReceiverEmail(storageSendMailInfo);

        if (mailTemplateVO == null || !StringUtils.hasLength(mailTemplateVO.getTemplate())) {
            mailTemplateVO = new MailTemplateVO();
            String templateContent = MailTemplateUtils.readContentFromTemplateFile(getDefaultTemplate(storageSendMailInfo.getSendType()).getTemplateName(),
                    getDefaultTemplate(storageSendMailInfo.getSendType()).getTemplateFolderPath());
            mailTemplateVO.setTemplate(templateContent);
            mailTemplateVO.setSubject(getDefaultTemplate(storageSendMailInfo.getSendType()).getSubject());
        }

        //判断传入的subject是否为null或者空
        String subject = getRightSubject(storageSendMailInfo.getSubject(), mailTemplateVO.getSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.parseHtmlMailTemplate(storageSendMailInfo.getReplacedMap(), mailTemplateVO.getTemplate());

        return sendEmail(subject, sendContent, storageSendMailInfo.getReceiverEmail());
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

    private MailTemplateEnum getDefaultTemplate(SendHtmlMailKeyNameEnum mailKeyNameEnum) throws IOException {
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
    private StorageSendMailInfo setReceiverEmail(StorageSendMailInfo storageSendMailInfo)
            throws ReflectiveOperationException, EmailException {
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

        return storageSendMailInfo;
    }

    private CommentDO getRepliedComment(StorageSendMailInfo storageSendMailInfo) {
        if (storageSendMailInfo.getSendType() != SendHtmlMailKeyNameEnum.REPLY_COMMENT) {
            return null;
        }

        // 是回复评论，则组装一个新的数据，用于发送收到评论的邮件
        Map<String, Object> additionalData = storageSendMailInfo.getAdditionalData();
        if (additionalData == null) {
            // 可能会存在用户消息乱发送请求，则直接跳过
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(ConvertObjectUtils.jsonToString(additionalData));
        return JSON.parseObject(jsonObject.getString(SendHtmlMailKeyNameEnum.ADDITIONAL_DATA.getKeyName()), CommentDO.class);
    }
}
