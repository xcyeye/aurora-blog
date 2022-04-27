package xyz.xcye.message.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.table.EmailLogDO;
import xyz.xcye.common.util.BeanUtils;
import xyz.xcye.common.util.DateUtils;
import xyz.xcye.common.vo.MailTemplateVO;
import xyz.xcye.message.enums.MailTemplateEnum;
import xyz.xcye.message.mail.SendMailRealize;
import xyz.xcye.message.service.EmailLogService;
import xyz.xcye.message.service.MailTemplateService;
import xyz.xcye.message.service.SendMailService;
import xyz.xcye.message.util.MailTemplateUtils;
import xyz.xcye.message.util.ParseEmailTemplate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

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

    /**
     * 邮件的最大标题长度
     */
    @Value("${aurora.mail.max-subject-length}")
    private int maxSubjectLength;

    @Override
    public ModifyResult sendHtmlMail(StorageSendMailInfo storageSendMailInfo)
            throws MessagingException, IOException, ReflectiveOperationException {
        String type = storageSendMailInfo.getType();

        // 1. 根据userUid和type从数据库中获取模板，如果没有的话，则使用默认的
        ConditionDTO<Long> condition = new ConditionDTO();
        condition.setOtherUid(storageSendMailInfo.getUserUid());
        condition.setKeyword(type);
        MailTemplateVO mailTemplateVO = BeanUtils.getSingleObjFromList(mailTemplateService.queryAllMailTemplate(condition), MailTemplateVO.class);

        if (mailTemplateVO == null || !StringUtils.hasLength(mailTemplateVO.getTemplate())) {
            mailTemplateVO = new MailTemplateVO();
            String templateContent = MailTemplateUtils.readContentFromTemplateFile(getDefaultTemplate(type).getTemplateName(),
                    getDefaultTemplate(type).getTemplateFolderPath());
            mailTemplateVO.setTemplate(templateContent);
            mailTemplateVO.setSubject(getDefaultTemplate(type).getSubject());
        }

        //判断传入的subject是否为null或者空
        String subject = getRightSubject(storageSendMailInfo.getSubject(), mailTemplateVO.getSubject());

        //解析邮件发送内容
        String sendContent = ParseEmailTemplate.parseHtmlMailTemplate(storageSendMailInfo.getReplacingMap(), mailTemplateVO.getTemplate());

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

    private MailTemplateEnum getDefaultTemplate(String type) throws IOException {
        switch (type) {
            case "receiveComment":
                return MailTemplateEnum.RECEIVE_COMMENT;
            case "replyComment":
                return MailTemplateEnum.REPLY_COMMENT;
            case "commonNotice":
                return MailTemplateEnum.COMMON_NOTICE;
            case "verifyAccount":
                return MailTemplateEnum.VERIFY_ACCOUNT;
            default:
                return MailTemplateEnum.DEFAULT;
        }

    }
}
