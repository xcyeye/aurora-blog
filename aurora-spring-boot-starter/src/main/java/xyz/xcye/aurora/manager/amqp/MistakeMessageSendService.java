package xyz.xcye.aurora.manager.amqp;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import xyz.xcye.common.constant.RabbitMQNameConstant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 错误消息发送服务
 * @author qsyyke
 */

@Component
@AllArgsConstructor
public class MistakeMessageSendService {

    private RabbitTemplate rabbitTemplate;

    /**
     * 如果从交换机中发送到某个队列的消息不符合规范，则将此消息发送到错误交换机中进行消费
     * @param msg
     * @param channel
     * @param message
     * @throws IOException
     */
    public void sendMistakeMessageToExchange(String msg, Channel channel, Message message) throws IOException {
        // 任何一个出问题，都表示生产者发送的消息不合法，将此消息发送到mistakeMessageExchange交换机 因为这个消息是没有用的，所以也就不更新数据库了
        rabbitTemplate.send(RabbitMQNameConstant.MISTAKE_MESSAGE_EXCHANGE, RabbitMQNameConstant.MISTAKE_MESSAGE_ROUTING_KEY,new Message(msg.getBytes(StandardCharsets.UTF_8)));
        //在此处需要应答
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
