package xyz.xcye.admin.manager.mq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

import java.io.IOException;

/**
 * 错误消息的消费者
 * @author qsyyke
 */

@Slf4j
@Component
public class MistakeMessageConsumer {

    /**
     * 专门消费生产者生产不合法的消息
     * @param msgJson
     * @param channel
     */
    @RabbitListener(queues = RabbitMQNameConstant.MISTAKE_MESSAGE_QUEUE,ackMode = "AUTO")
    public void mistakeMessageConsumer(String msgJson, Channel channel, Message message) throws IOException {
        log.error("无法消费的消息: {}",msgJson);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
