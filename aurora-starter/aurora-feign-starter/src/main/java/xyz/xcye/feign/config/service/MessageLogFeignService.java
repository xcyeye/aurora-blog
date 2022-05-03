package xyz.xcye.feign.config.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.po.MessageLog;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:56
 */

@Component
@FeignClient(value = "aurora-message")
public interface MessageLogFeignService {
    @PostMapping("/message/messageLog")
    R insertMessageLog(@SpringQueryMap MessageLog messageLog) throws BindException;

    @PutMapping("/message/messageLog")
    R updateMessageLog(@SpringQueryMap MessageLog messageLog) throws BindException;

    @GetMapping("/message/messageLog/{uid}")
    R queryMessageLogByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException;
}
