package xyz.xcye.comment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.entity.result.ModifyResult;


/**
 * 使用openFeign远程调用mq消息相关接口
 * @author qsyyke
 */

@Component
@FeignClient(value = "aurora-message",contextId = "aurora-message-messageLog")
public interface MessageLogFeignService {

    /**
     * 插入新mq消息
     * @param messageLogDO
     * @return
     * @throws BindException
     */
    @PostMapping("/message/messageLog/insert")
    ModifyResult insertMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    /**
     * 更新mq消息
     * @param messageLogDO
     * @return
     * @throws BindException
     */
    @PutMapping("/message/messageLog/update")
    ModifyResult updateMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    /**
     * 根据uid查询mq消息
     * @param uid
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @GetMapping("/message/messageLog/queryByUid/{uid}")
    MessageLogDO queryMessageLogByUid(@PathVariable long uid) throws InstantiationException, IllegalAccessException;
}