package xyz.xcye.feign.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.util.BeanUtils;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.vo.MessageLogVO;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:56
 */

@FeignClient(value = "aurora-message", name = "aurora-message")
public interface MessageLogFeignService {
    @PostMapping("/message/messageLog")
    R insertMessageLog(@SpringQueryMap MessageLog messageLog) throws BindException;

    @PutMapping("/message/messageLog")
    R updateMessageLog(@SpringQueryMap MessageLog messageLog) throws BindException;

    @GetMapping("/message/messageLog/{uid}")
    R queryMessageLogByUid(@PathVariable("uid") long uid);

    @Component
    static class UpdateMessageLog {

        @Autowired
        private MessageLogFeignService messageLogFeignService;

        /**
         * 更新数据库中的mq消息的信息
         * @param correlationDataId
         * @param ackStatus
         * @param consumeStatus
         * @param errorMessage
         * @throws BindException
         */
        public void updateMessageLogInfo(String correlationDataId, boolean ackStatus,
                                          boolean consumeStatus, String errorMessage) throws BindException {
            MessageLogVO messageLogVO = null;
            try {
                messageLogVO = (MessageLogVO) messageLogFeignService.queryMessageLogByUid(Long.parseLong(correlationDataId)).getData();
            } catch (NumberFormatException e) {
                return;
            }

            if (messageLogVO == null) {
                return;
            }

            messageLogVO.setAckStatus(ackStatus);
            messageLogVO.setConsumeStatus(consumeStatus);
            messageLogVO.setErrorMessage(errorMessage);
            messageLogFeignService.updateMessageLog(BeanUtils.copyProperties(messageLogVO, MessageLog.class));
        }
    }
}
