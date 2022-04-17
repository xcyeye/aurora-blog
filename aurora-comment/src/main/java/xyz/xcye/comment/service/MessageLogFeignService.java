package xyz.xcye.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.entity.result.ModifyResult;


/**
 * @author qsyyke
 */

@Component
@FeignClient(value = "aurora-message",contextId = "aurora-message-messageLog")
public interface MessageLogFeignService {

    @PostMapping("/message/messageLog/insert")
    ModifyResult insertMessageLog(@SpringQueryMap MessageLogDO messageLogDO, @RequestParam("xid") String xid) throws BindException;


    @PutMapping("/message/messageLog/update")
    ModifyResult updateMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @GetMapping("/message/messageLog/queryByUid/{uid}")
    MessageLogDO queryMessageLogByUid(@PathVariable long uid) throws InstantiationException, IllegalAccessException;

    @PostMapping("/message/messageLog/seataTest")
    void testMethod(@RequestParam(value = "xid",required = false) String xid) throws BindException;
}