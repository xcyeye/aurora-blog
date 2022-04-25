package xyz.xcye.message.manager.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.ConditionDTO;
import xyz.xcye.common.vo.MessageLogVO;
import xyz.xcye.message.service.MessageLogService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 定时向数据库中查询那些没有被消费的数据，重新发送
 * @author qsyyke
 */

@Slf4j
@Component
public class RabbitMQSchedule {

    @Autowired
    private MessageLogService messageLogService;

    @Value("${aurora.mq.max-retry-consume}")
    private int maxRetryConsumeMq;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 重新消费数据库中消费失败的rabbitmq消息
     */
    //@Scheduled(cron = CrontabExpressionConstant.RETRY_CONSUME_MQ_MESSAGE)
    public void reconsumeMQMessageTask() throws BindException {
        // 1. 从数据库中获取所有没有消费的数据
        MessageLogDO messageLogDO = new MessageLogDO();
        messageLogDO.setConsumeStatus(false);
        List<MessageLogVO> messageLogVOList = null;
        try {
            messageLogVOList = messageLogService.queryAllMessageLog(new ConditionDTO<>());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error("定时器获取rabbitmq消费日志失败: {}",e.getMessage());
            return;
        }

        for (MessageLogVO messageLogVO : messageLogVOList) {
            // 判断是否达到了最大重发次数
            Integer tryCount = messageLogVO.getTryCount();
            if (tryCount >= maxRetryConsumeMq) {
                // 退出
                log.warn("{}已达到最大重发次数",messageLogVO);
                continue;
            }

            // 进行重发
            rabbitTemplate.send(messageLogVO.getExchange(),messageLogVO.getRoutingKey(),
                    new Message(messageLogVO.getMessage().getBytes(StandardCharsets.UTF_8)));

            // 修改消费日志
            MessageLogDO resendMsgMessageLogDO = new MessageLogDO();
            BeanUtils.copyProperties(messageLogVO,resendMsgMessageLogDO);
            resendMsgMessageLogDO.setTryCount(messageLogVO.getTryCount() + 1);
            messageLogService.updateMessageLog(resendMsgMessageLogDO);
        }
    }
}
