package xyz.xcye.admin.manager.mq.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.xcye.amqp.config.AuroraAmqpProperties;
import xyz.xcye.core.util.LogUtils;

import java.util.Optional;

/**
 * 注册rabbitmq的交换机，队列，以及将交换机和队列绑定起来
 *
 * @author qsyyke
 * @date Created in 2022/6/1 17:02
 */

@Slf4j
@Component
public class AmqpRegister {

    @Autowired
    private AuroraAmqpProperties auroraAmqpProperties;

    /**
     * 过期时间
     */
    private final int ttl = 6000;

    /**
     * 队列最大长度
     */
    private final int maxLength = 30;

    @Autowired
    private AmqpAdmin amqpAdmin;

    // 注册交换机
    @Bean
    public void registerExchanges() {
        auroraAmqpProperties.getExchanges().stream()
                .filter(amqpExchange -> StringUtils.hasLength(amqpExchange.getExchange()))
                .forEach(amqpExchange -> {
                    // 创建普通交换机
                    CustomExchange commonExchange = new CustomExchange(amqpExchange.getExchange(),
                            amqpExchange.getExchangeType(), true, false);

                    // 创建死信交换机
                    CustomExchange deadLetterExchange = new CustomExchange(amqpExchange.getDeadLetterExchange(),
                            amqpExchange.getDeadLetterExchangeType(), true, false);

                    try {
                        // 声明普通交换机
                        amqpAdmin.declareExchange(commonExchange);

                        // 声明死信交换机
                        amqpAdmin.declareExchange(deadLetterExchange);
                    } catch (Exception e) {
                        log.error("声明rabbitmq交换机出错, 普通交换机名称: {}, 死信交换机名称{}",
                                amqpExchange.getExchange(), amqpExchange.getDeadLetterExchange());
                        LogUtils.logExceptionInfo(e);
                    }
                });
    }

    // 注册队列
    @Bean
    public void registerQueues() {
        auroraAmqpProperties.getExchanges().stream()
                .filter(amqpExchange -> StringUtils.hasLength(amqpExchange.getExchange()))
                .forEach(amqpExchange -> {
                    amqpExchange.getQueues().forEach(amqpQueue -> {
                        // 声明过期时间和队列最大长度，优先使用每个list中的，
                        // 其次是配置的默认失效时间和最大长度，如果都是null的话，在使用该类中的ttl和maxLength
                        Integer declareTtl = Optional.ofNullable(amqpQueue.getTtl())
                                .orElse(auroraAmqpProperties.getDefaultTtl());
                        Integer declareMaxLength = Optional.ofNullable(amqpQueue.getMaxLength())
                                .orElse(auroraAmqpProperties.getDefaultMaxLength());

                        // 创建普通队列
                        Queue commonQueue = QueueBuilder.durable(amqpQueue.getQueue())
                                .ttl(Optional.ofNullable(declareTtl).orElse(ttl))
                                .maxLength(Optional.ofNullable(declareMaxLength).orElse(maxLength))
                                // 将此队列和对应的死信交换机绑定
                                .deadLetterExchange(amqpExchange.getDeadLetterExchange())
                                .deadLetterRoutingKey(amqpQueue.getDeadLetterRoutingKey())
                                .build();

                        // 创建死信队列
                        Queue deadLetterQueue = QueueBuilder.durable(amqpQueue.getDeadLetterQueue()).build();

                        try {
                            // 声明普通队列和死信队列
                            amqpAdmin.declareQueue(commonQueue);
                            amqpAdmin.declareQueue(deadLetterQueue);
                        } catch (Exception e) {
                            log.error("声明rabbitmq队列出错, 普通队列名称: {}, 死信队列名称{}",
                                    amqpQueue.getQueue(), amqpQueue.getDeadLetterQueue());
                            LogUtils.logExceptionInfo(e);
                        }
                    });
                });
    }

    // 注册队列和交换机的绑定
    @Bean
    public void registerBinding() {
        auroraAmqpProperties.getExchanges().stream()
                .filter(amqpExchange -> StringUtils.hasLength(amqpExchange.getExchange()))
                .forEach(amqpExchange -> {
                    amqpExchange.getQueues().forEach(amqpQueue -> {
                        // 创建一个普通绑定，将普通交换机和普通队列进行绑定
                        Binding commonBinding = BindingBuilder.bind(new Queue(amqpQueue.getQueue()))
                                .to(new CustomExchange(amqpExchange.getExchange(), amqpExchange.getExchangeType()))
                                .with(amqpQueue.getRoutingKey()).noargs();

                        // 创建一个死信绑定，将死信交换机和死信队列进行绑定
                        Binding deadLetterBinding = BindingBuilder.bind(new Queue(amqpQueue.getDeadLetterQueue()))
                                .to(new CustomExchange(amqpExchange.getDeadLetterExchange(), amqpExchange.getDeadLetterExchangeType()))
                                .with(amqpQueue.getDeadLetterRoutingKey()).noargs();

                        try {
                            // 声明绑定
                            amqpAdmin.declareBinding(commonBinding);
                            amqpAdmin.declareBinding(deadLetterBinding);
                        } catch (Exception e) {
                            log.error("声明rabbitmq绑定出错, 普通交换机名称: {}, 死信交换机名称{}",
                                    amqpExchange.getExchange(), amqpExchange.getDeadLetterExchange());
                            LogUtils.logExceptionInfo(e);
                        }
                    });
                });
    }
}
