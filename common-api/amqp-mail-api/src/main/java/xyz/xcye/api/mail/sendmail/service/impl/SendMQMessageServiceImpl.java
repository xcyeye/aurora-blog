package xyz.xcye.api.mail.sendmail.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.api.AmqpSenderService;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.api.mail.sendmail.service.SendMQMessageService;
import xyz.xcye.api.mail.sendmail.util.StorageMailUtils;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.enums.RegexEnum;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;
import xyz.xcye.core.exception.email.EmailException;
import xyz.xcye.core.util.ConvertObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 * @date Created in 2022/4/28 08:41
 */

@Slf4j
@Service
public class SendMQMessageServiceImpl implements SendMQMessageService {

    @Autowired
    private AmqpSenderService amqpSenderService;

    @Override
    public void sendReplyMail(Comment replyingCommentInfo, Comment repliedCommentInfo, String exchangeName,
                              String exchangeType, String routingKey) throws BindException {
        // 组装新评论对象，发送给被评论页面所对应的用户
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setSubject(replyingCommentInfo.getContent());
        mailInfo.setUserUid(repliedCommentInfo.getUserUid());

        // 如果不是回复评论的话，则直接传入userUid便可以，会通过此userUid查询对应的email，但是如果是回复评论，则需要在此处进行设置收件人邮箱
        // 优先级：receiverEmail > 通过userUid查询到的email
        mailInfo.setReceiverEmail(repliedCommentInfo.getEmail());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.REPLY_COMMENT);

        List<Map<SendHtmlMailTypeNameEnum,Object>> list = new ArrayList<>();
        Map<SendHtmlMailTypeNameEnum, Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT,replyingCommentInfo);
        map.put(SendHtmlMailTypeNameEnum.REPLY_COMMENT, repliedCommentInfo);
        list.add(map);

        // 将组装的map集合转换成json字符串，发送到交换机
        mailInfo = StorageMailUtils.generateMailInfo(mailInfo, list);

        // 组装一个存放被回复评论对象的数据
        Map<String,Object> repliedMap = new HashMap<>();
        repliedMap.put(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.getKeyName(), replyingCommentInfo);
        mailInfo.setAdditionalData(repliedMap);
        String msgJson = ConvertObjectUtils.jsonToString(mailInfo);
        amqpSenderService.sendMQMsg(msgJson, exchangeName, routingKey, exchangeType);
    }

    @Override
    public void sendCommonMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                               String routingKey, List<Map<SendHtmlMailTypeNameEnum,Object>> replacedObjList) throws AuroraException, BindException {
        if (isLegitimateReceiverEmail(sendMailInfo)) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }

        // 将发送的回复评论数据组装成一个map集合
        String msgJson = StorageMailUtils.generateMailJson(sendMailInfo, replacedObjList);
        amqpSenderService.sendMQMsg(msgJson, exchangeName, routingKey, exchangeType);
    }

    @Override
    public void sendSimpleTextMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                                   String routingKey) throws AuroraException, BindException {
        // 发送简单文本
        if (!StringUtils.hasLength(sendMailInfo.getSimpleText())) {
            // 没有简单文本，不做处理
            return;
        }

        if (isLegitimateReceiverEmail(sendMailInfo)) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }

        //将发送的回复评论数据组装成一个map集合
        String msgJson = StorageMailUtils.generateMailJson(sendMailInfo, null);
        amqpSenderService.sendMQMsg(msgJson, exchangeName, routingKey, exchangeType);
    }

    /**
     * 判断待发送的邮件对象中的数据，是否合法
     * @param mailInfo
     * @return
     */
    private boolean isLegitimateReceiverEmail(StorageSendMailInfo mailInfo) {
        if (!StringUtils.hasLength(mailInfo.getReceiverEmail()) && mailInfo.getUserUid() == null) {
            return true;
        }

        if (StringUtils.hasLength(mailInfo.getReceiverEmail())) {
            boolean matches = Pattern.matches(RegexEnum.MAIL_REGEX.getRegex(), mailInfo.getReceiverEmail());
            if (!matches) {
                mailInfo.setReceiverEmail(null);
            }
        }
        return false;
    }
}
