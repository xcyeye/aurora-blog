package xyz.xcye.comment.manager.amqp.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import xyz.xcye.amqp.api.AmqpSenderService;
import xyz.xcye.comment.po.Comment;
import xyz.xcye.core.constant.amqp.AmqpExchangeNameConstant;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.util.ConvertObjectUtils;

/**
 * 将新评论发送到aurora.send.comment.exchange交换机中，文章服务消费
 * @author qsyyke
 * @date Created in 2022/5/12 15:26
 */

@Component
public class SendCommentToExchange {

    @Autowired
    private AmqpSenderService amqpSenderService;

    @Transactional
    public void sendCommentToMQ(Comment comment) throws BindException {
        String json = ConvertObjectUtils.jsonToString(comment);
        amqpSenderService.sendMQMsg(comment.getUid() + "", json, AmqpExchangeNameConstant.AURORA_SEND_COMMENT_EXCHANGE,
                AmqpQueueNameConstant.PAGE_COMMENT_ROUTING_KEY, "topic");
    }
}
