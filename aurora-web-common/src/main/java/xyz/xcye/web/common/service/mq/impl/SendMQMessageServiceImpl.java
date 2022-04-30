package xyz.xcye.web.common.service.mq.impl;

import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.common.entity.table.CommentDO;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.enums.RegexEnum;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.common.exception.AuroraGlobalException;
import xyz.xcye.common.exception.email.EmailException;
import xyz.xcye.common.util.ConvertObjectUtils;
import xyz.xcye.common.util.ValidationUtils;
import xyz.xcye.common.util.id.GenerateInfoUtils;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.web.common.service.feign.MessageLogFeignService;
import xyz.xcye.web.common.service.mq.SendMQMessageService;

import javax.annotation.Resource;
import javax.validation.groups.Default;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author qsyyke
 * @date Created in 2022/4/28 08:41
 */

@Service
public class SendMQMessageServiceImpl implements SendMQMessageService {

    /**
     * 当前机器的id
     */
    @Value("${aurora.snow-flake.workerId}")
    private int workerId;

    /**
     * 该台机器对应的数据中心id
     */
    @Value("${aurora.snow-flake.datacenterId}")
    private int datacenterId;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MessageLogFeignService messageLogFeignService;

    @Override
    public void sendReplyMail(CommentDO replyingCommentInfo, CommentDO repliedCommentInfo, String exchangeName,
                              String exchangeType, String routingKey) throws BindException {
        // 组装新评论对象，发送给被评论页面所对应的用户
        StorageSendMailInfo mailInfo = new StorageSendMailInfo();
        mailInfo.setSubject(replyingCommentInfo.getContent());
        mailInfo.setUserUid(repliedCommentInfo.getUserUid());
        setCorrelationDataId(mailInfo);

        // 如果不是回复评论的话，则直接传入userUid便可以，会通过此userUid查询对应的email，但是如果是回复评论，则需要在此处进行设置收件人邮箱
        // 优先级：receiverEmail > 通过userUid查询到的email
        mailInfo.setReceiverEmail(repliedCommentInfo.getEmail());
        mailInfo.setSendType(SendHtmlMailTypeNameEnum.REPLY_COMMENT);

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(SendHtmlMailTypeNameEnum.RECEIVE_COMMENT.getKeyName(),replyingCommentInfo);
        map.put(SendHtmlMailTypeNameEnum.REPLY_COMMENT.getKeyName(), repliedCommentInfo);
        list.add(map);

        // 将组装的map集合转换成json字符串，发送到交换机
        mailInfo = ConvertObjectUtils.generateMailInfo(mailInfo, list);

        // 组装一个存放被回复评论对象的数据
        Map<String,Object> repliedMap = new HashMap<>();
        repliedMap.put(SendHtmlMailTypeNameEnum.ADDITIONAL_DATA.getKeyName(), replyingCommentInfo);
        mailInfo.setAdditionalData(repliedMap);
        String msgJson = ConvertObjectUtils.jsonToString(mailInfo);

        insertMessageLogData(mailInfo, msgJson, exchangeName, routingKey, exchangeType);
        sendMQMsg(mailInfo, msgJson, exchangeName, routingKey, exchangeType);
    }

    @Override
    public void sendCommonMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                               String routingKey, List<Map<String,Object>> replacedObjList)
            throws AuroraGlobalException, BindException {
        if (isLegitimateReceiverEmail(sendMailInfo)) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }
        setCorrelationDataId(sendMailInfo);

        //将发送的回复评论数据组装成一个map集合
        String msgJson = ConvertObjectUtils.generateMailJson(sendMailInfo, replacedObjList);
        sendMQMsg(sendMailInfo, msgJson, exchangeName, routingKey, exchangeType);
    }

    @Override
    public void sendSimpleTextMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                                   String routingKey) throws AuroraGlobalException, BindException {
        // 发送简单文本
        if (!StringUtils.hasLength(sendMailInfo.getSimpleText())) {
            // 没有简单文本，不做处理
            return;
        }

        if (isLegitimateReceiverEmail(sendMailInfo)) {
            throw new EmailException(ResponseStatusCodeEnum.EXCEPTION_EMAIL_EXISTS);
        }

        setCorrelationDataId(sendMailInfo);

        //将发送的回复评论数据组装成一个map集合
        String msgJson = ConvertObjectUtils.generateMailJson(sendMailInfo, null);
        sendMQMsg(sendMailInfo, msgJson, exchangeName, routingKey, exchangeType);

        System.out.println("手动提交");
        try {
            GlobalTransactionContext.getCurrent().commit();
            System.out.println("提交完成");
        } catch (TransactionException e) {
            e.printStackTrace();
            System.out.println("提交失败");
        }
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

    private void setCorrelationDataId(StorageSendMailInfo mailInfo) {
        if (!StringUtils.hasLength(mailInfo.getCorrelationDataId())) {
            mailInfo.setCorrelationDataId(GenerateInfoUtils.generateUid(workerId, datacenterId) + "");
        }
    }

    private MessageLogDO setMessageLogDO(String message,long uid,String exchange,
                                         String queue,String routingKey,boolean ackStatus,
                                         int tryCount,String exchangeType,boolean consumeStatus,
                                         String errorMessage) {
        MessageLogDO messageLogDO = new MessageLogDO();
        messageLogDO.setMessage(message);
        messageLogDO.setUid(uid);
        messageLogDO.setExchange(exchange);
        messageLogDO.setRoutingKey(routingKey);
        messageLogDO.setAckStatus(ackStatus);
        messageLogDO.setTryCount(tryCount);
        messageLogDO.setExchangeType(exchangeType);
        messageLogDO.setConsumeStatus(consumeStatus);
        messageLogDO.setErrorMessage(errorMessage);
        return messageLogDO;
    }

    private void sendMQMsg(StorageSendMailInfo sendMailInfo, String msgJson, String exchangeName, String routingKey, String exchangeType) throws BindException {
        CorrelationData correlationData = new CorrelationData(sendMailInfo.getCorrelationDataId());

        // 调用feign向数据库中插入mq消息
        insertMessageLogData(sendMailInfo, msgJson, exchangeName, routingKey, exchangeType);
        rabbitTemplate.send(exchangeName, routingKey, new Message(msgJson.getBytes(StandardCharsets.UTF_8)), correlationData);
    }

    private void insertMessageLogData(StorageSendMailInfo mailInfo, String msgJson, String exchangeName, String routingKey, String exchangeType) throws BindException {
        //向au_message_log表中插入生产信息
        MessageLogDO messageLogDO = setMessageLogDO(msgJson, Long.parseLong(mailInfo.getCorrelationDataId()), exchangeName, "",
                routingKey, false, 0, exchangeType, false, "");
        // 验证messageLogDO对象属性是否合法
        ValidationUtils.valid(messageLogDO, Insert.class, Default.class);
        messageLogFeignService.insertMessageLog(messageLogDO);
    }
}
