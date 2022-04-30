package xyz.xcye.web.common.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.entity.table.EmailDO;
import xyz.xcye.common.entity.table.MessageLogDO;

@Component
@FeignClient(value = "aurora-message",name = "aurora-message",
        contextId = "globalAuroraMessageLog",
        qualifiers = "globalAuroraMessageLog")
public interface MessageLogFeignService {

    @PostMapping("/message/messageLog")
    R insertMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @PutMapping("/message/messageLog")
    R updateMessageLog(@SpringQueryMap MessageLogDO messageLogDO) throws BindException;

    @DeleteMapping("/message/messageLog/delete/{uid}")
    R deleteMessageLog(@PathVariable("uid") long uid);

    @GetMapping("/message/messageLog")
    R queryAllMessageLog(@SpringQueryMap MessageLogDO messageLogDO, @SpringQueryMap PaginationDTO paginationDTO) throws ReflectiveOperationException;

    @GetMapping("/message/messageLog/{uid}")
    R queryMessageLogByUid(@PathVariable("uid") long uid);

    @PostMapping("/message/email")
    R insertEmail(@SpringQueryMap EmailDO email);

    @DeleteMapping("/message/email/{uid}")
    R deleteEmailByUid(@PathVariable(value = "uid") long uid);

    @DeleteMapping("/message/email/deleteStatus")
    R updateDeleteStatus(@SpringQueryMap EmailDO email);

    @PutMapping("/message/email")
    R updateEmailByUid(@SpringQueryMap EmailDO email);

    @GetMapping("/message/email/{uid}")
    R queryEmailByUid(@PathVariable("uid") long uid);

    @GetMapping("/message/email/userUid/{userUid}")
    R queryByUserUid(@PathVariable("userUid") long userUid);

    @GetMapping("/message/email/email/{email}")
    R queryByEmail(@PathVariable("email") String email);
}