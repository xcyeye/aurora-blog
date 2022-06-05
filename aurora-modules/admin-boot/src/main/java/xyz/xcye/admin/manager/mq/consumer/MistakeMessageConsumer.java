package xyz.xcye.admin.manager.mq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.xcye.core.constant.amqp.AmqpQueueNameConstant;
import xyz.xcye.core.util.LogUtils;

import java.io.IOException;

/**
 * 错误消息的消费者
 * @author qsyyke
 */

@Component
public class MistakeMessageConsumer {

    /**
     * 专门消费生产者生产的不合法消息，对这些不合法的信息，只记录，不进行任何的处理，比如入库等操作
     * @param msgJson
     * @param channel
     */
    @RabbitListener(queues = AmqpQueueNameConstant.MISTAKE_MESSAGE_QUEUE,ackMode = "AUTO")
    public void mistakeMessageConsumer(String msgJson, Channel channel, Message message) throws IOException {
        LogUtils.logMistakeMessage(msgJson);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
