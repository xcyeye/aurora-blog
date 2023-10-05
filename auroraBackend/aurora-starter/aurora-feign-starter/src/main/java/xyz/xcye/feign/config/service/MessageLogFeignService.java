package xyz.xcye.feign.config.service;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.core.util.JSONUtils;
import xyz.xcye.core.util.LogUtils;
import xyz.xcye.message.pojo.MessageLogPojo;
import xyz.xcye.message.vo.MessageLogVO;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:56
 */

@FeignClient(value = "aurora-message", name = "aurora-message", fallback = MessageLogFeignHandler.class)
public interface MessageLogFeignService {
    @PostMapping("/message/messageLog/insertMessageLog")
    R insertMessageLog(@RequestBody MessageLogPojo messageLog) throws BindException;

    @PostMapping("/message/messageLog/updateMessageLog")
    R updateMessageLog(@RequestBody MessageLogPojo messageLog) throws BindException;

    @PostMapping("/message/messageLog/queryMessageLogByUid")
    R queryMessageLogByUid(@RequestBody MessageLogPojo messageLog);

    @Component
    static class UpdateMessageLog {

        @Autowired
        private MessageLogFeignService messageLogFeignService;

        /**
         * 更新数据库中的mq消息的信息
         *
         * @param correlationDataId
         * @param ackStatus
         * @param consumeStatus
         * @param errorMessage
         * @throws BindException
         */
        public void updateMessageLogInfo(String correlationDataId, boolean ackStatus,
                                         boolean consumeStatus, String errorMessage, Message message) throws BindException {
            MessageLogVO messageLogVO = null;
            try {
                R r = messageLogFeignService.queryMessageLogByUid(new MessageLogPojo() {{
                    setUid(Long.parseLong(correlationDataId));
                }});
                messageLogVO = JSONUtils.parseObjFromResult(r, "data", MessageLogVO.class);
            } catch (Exception e) {
                LogUtils.logExceptionInfo(e);
                return;
            }

            if (messageLogVO == null) {
                return;
            }

            messageLogVO.setAckStatus(ackStatus);
            messageLogVO.setConsumeStatus(consumeStatus);
            messageLogVO.setErrorMessage(errorMessage);
            messageLogFeignService.updateMessageLog(BeanUtils.copyProperties(messageLogVO, MessageLogPojo.class));
        }
    }
}
