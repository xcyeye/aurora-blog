package xyz.xcye.web.common.service.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.List;

@Service
@FeignClient(value = "aurora-message",
        fallback = MessageLogFeignService.MessageLogFeignServiceFallBack.class,
        contextId = "aurora-message-messageLog")
public interface MessageLogFeignService {

    @PostMapping("/message/messageLog")
    ModifyResult insertMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @PutMapping("/message/messageLog")
    ModifyResult updateMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @DeleteMapping("/message/messageLog/delete/{uid}")
    ModifyResult deleteMessageLog(@PathVariable("uid") long uid);

    @GetMapping("/message/messageLog")
    List<MessageLogVO> queryAllMessageLog(@SpringQueryMap MessageLogDO messageLogDO, @SpringQueryMap PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException;

    @GetMapping("/message/messageLog/{uid}")
    MessageLogDO queryMessageLogByUid(@PathVariable("uid") long uid);

    @Slf4j
    class MessageLogFeignServiceFallBack implements MessageLogFeignService {
        @Override
        public ModifyResult insertMessageLog(MessageLogDO messageLogDO) throws BindException {
            log.error("发生异常了");
            return ModifyResult.operateResult("insertMessageLog发生意外了",200,200);
        }

        @Override
        public ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException {
            log.error("发生异常了");
            return ModifyResult.operateResult("updateMessageLog发生意外了",200,200);
        }

        @Override
        public ModifyResult deleteMessageLog(long uid) {
            log.error("发生异常了");
            return ModifyResult.operateResult("deleteMessageLog发生意外了",200,200);
        }

        @Override
        public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
            log.error("发生异常了");
            return null;
        }

        @Override
        public MessageLogDO queryMessageLogByUid(long uid) {
            log.error("发生异常了");
            return null;
        }
    }
}