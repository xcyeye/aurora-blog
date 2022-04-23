package xyz.xcye.web.common.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.List;

@FeignClient(value = "aurora-message",name = "aurora-message",
        fallback = MessageLogFeignServiceFallBack.class,
        contextId = "globalAuroraMessageLog",
        qualifiers = "globalAuroraMessageLog")
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

    @PostMapping("/message/email")
    ModifyResult insertEmail(@SpringQueryMap EmailDO email);

    @DeleteMapping("/message/email/{uid}")
    ModifyResult deleteEmailByUid(@PathVariable(value = "uid") long uid);

    @DeleteMapping("/message/email/deleteStatus")
    ModifyResult updateDeleteStatus(@SpringQueryMap EmailDO email);

    @PutMapping("/message/email")
    ModifyResult updateEmailByUid(@SpringQueryMap EmailDO email);

    @GetMapping("/message/email/{uid}")
    EmailDO queryByUid(@PathVariable("uid") long uid);

    @GetMapping("/message/email/userUid/{userUid}")
    EmailDO queryByUserUid(@PathVariable("userUid") long userUid);

    @GetMapping("/message/email/email/{email}")
    EmailDO queryByEmail(@PathVariable("email") String email);
}