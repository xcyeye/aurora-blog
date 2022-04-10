package xyz.xcye.comment.manager.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.xcye.common.dos.CommentDO;
import xyz.xcye.common.enums.RabbitMQNameEnum;
import xyz.xcye.common.util.ObjectConvertJson;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author qsyyke
 */

@Slf4j
@Component
public class RabbitMQSendService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendReceiveCommentNotice(CommentDO receiveCommentInfo) {
        //将发送的回复评论数据组装成一个map集合
        String jsonToString = ObjectConvertJson.jsonToString(receiveCommentInfo);

        rabbitTemplate.send(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE,
                RabbitMQNameEnum.MAIL_RECEIVE_COMMENT_NOTICE_ROUTING_KEY,
                new Message(jsonToString.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     *
     * @param replyingCommentInfo
     * @param repliedCommentInfo
     * @return
     */
    public void sendReplyCommentNotice(CommentDO replyingCommentInfo,CommentDO repliedCommentInfo) {
        //将发送的回复评论数据组装成一个map集合
        Map<String,CommentDO> commentMap = new HashMap<>();
        commentMap.put("replyingCommentInfo",replyingCommentInfo);
        commentMap.put("repliedCommentInfo",repliedCommentInfo);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.error("发布确认: {}",ack);
        });

        String commentJson = ObjectConvertJson.jsonToString(commentMap);
        rabbitTemplate.send(RabbitMQNameEnum.AURORA_SEND_EMAIL_COMMON_EXCHANGE,
                RabbitMQNameEnum.MAIL_REPLY_COMMENT_NOTICE_ROUTING_KEY,
                new Message(commentJson.getBytes(StandardCharsets.UTF_8)));
    }

}
