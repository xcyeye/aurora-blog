package xyz.xcye.message.manager.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author qsyyke
 */

@Slf4j
@Component
public class Tedy {

    @RabbitListener(queues = "test-que",ackMode = "MANUAL")
    public void tes(String msg, Channel channel, Message message) {
        try {
            /*System.out.println(message.getMessageProperties().getDeliveryTag());
            if (message.getMessageProperties().getDeliveryTag() % 2 == 0) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            }*/

            try {
                Thread.sleep(3000);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                log.error("获取到的id {}",new String(message.getBody()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*System.out.println(message.getMessageProperties().getDeliveryTag());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
    }
}
