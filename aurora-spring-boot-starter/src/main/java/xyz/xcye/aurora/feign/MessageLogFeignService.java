package xyz.xcye.aurora.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.entity.table.MessageLogDO;

@Component
@FeignClient(value = "aurora-message",name = "aurora-message")
public interface MessageLogFeignService {

    @PostMapping("/message/messageLog")
    R insertMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @PutMapping("/message/messageLog")
    R updateMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @GetMapping("/message/messageLog/{uid}")
    R queryMessageLogByUid(@PathVariable("uid") long uid);
}