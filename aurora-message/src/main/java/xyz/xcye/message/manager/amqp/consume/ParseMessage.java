package xyz.xcye.message.manager.amqp.consume;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.xcye.common.dto.StorageSendMailInfo;
import xyz.xcye.aurora.manager.amqp.MistakeMessageSendService;

import java.io.IOException;

/**
 * @author qsyyke
 * @date Created in 2022/4/27 16:58
 */

@Slf4j
@Component
public class ParseMessage {

    @Autowired
    private MistakeMessageSendService mistakeMessageSendService;

    public StorageSendMailInfo getStorageSendMailInfoFromMsg(String msgJson, Channel channel, Message message)
            throws IOException {
        StorageSendMailInfo storageSendMailInfo = null;
        try {
            storageSendMailInfo = JSON.parseObject(msgJson, StorageSendMailInfo.class);
        } catch (Exception e) {
            log.error("消费邮件发送的mq消息时，获取到错误消息{}",e.getMessage(),e);
            return null;
        }
        return storageSendMailInfo;
    }
}
