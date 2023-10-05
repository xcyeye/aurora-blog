package xyz.xcye.api.mail.sendmail.service;

import org.springframework.validation.BindException;
import xyz.xcye.api.mail.sendmail.entity.StorageSendMailInfo;
import xyz.xcye.api.mail.sendmail.enums.SendHtmlMailTypeNameEnum;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.exception.AuroraException;

import java.util.List;
import java.util.Map;

/**
 * 这是一个发送mq消息的服务，因为回复评论需要特殊处理，所以发送回复评论的时候，需要传入两个对象
 *
 * @author qsyyke
 * @date Created in 2022/4/28 08:34
 */

public interface SendMQMessageService {
    void sendReplyMail(Comment replyingCommentInfo, Comment repliedCommentInfo, String exchangeName,
                       String exchangeType, String routingKey) throws BindException;

    /**
     * 发送html邮件，其中如果sendMailInfo对象中，没有对receiverEmail赋值，那么会通过userUid向数据库中查询
     * ，使用查询到的结果作为收件人邮箱，如果receiverEmail和userUid都为null，或者receiverEmail为空，userUid为null，那么不会发送任何内容
     *
     * @param sendMailInfo
     */
    void sendCommonMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                        String routingKey, List<Map<SendHtmlMailTypeNameEnum, Object>> replacedObjList) throws AuroraException, BindException;

    /**
     * 发送简单文本邮件，其中如果sendMailInfo对象中，没有对receiverEmail赋值，那么会通过userUid向数据库中查询
     * ，使用查询到的结果作为收件人邮箱，如果receiverEmail和userUid都为null，或者receiverEmail为空，userUid为null，那么不会发送任何内容
     *
     * @param sendMailInfo
     */
    void sendSimpleTextMail(StorageSendMailInfo sendMailInfo, String exchangeName, String exchangeType,
                            String routingKey) throws AuroraException, BindException;
}
