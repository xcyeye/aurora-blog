package xyz.xcye.comment.manager.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xcye.common.util.id.GenerateInfoUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author qsyyke
 */

@Slf4j
@RestController
public class Test {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("test-dir");
    }

    @Bean
    public Queue queue() {
        return new Queue("test-que");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(new Queue("test-que"))
                .to(new DirectExchange("test-dir"))
                .with("dir-rout");
    }

    @Autowired
    private RabbitTemplate template;

    @GetMapping("/send")
    public String test(String message) {

        long uid = GenerateInfoUtils.generateUid(1, 1);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryTag(uid);
        //correlationData.setId(uid + "");
        template.send("test-dir","dir-rout",
                new Message(message.getBytes(StandardCharsets.UTF_8),messageProperties));
        log.info("发送成功 setId{}",uid);
        return message;
    }
}
