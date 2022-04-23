package xyz.xcye.admin.feign;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;

import java.util.List;

/**
 * 远程调用EmailController
 * @author qsyyke
 */


/*@FeignClient(value = "aurora-message",
        contextId = "adminAuroraMessageEmail",
        qualifiers = "adminAuroraMessageEmail")
public interface EmailFeignService {

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

    @GetMapping("/message/email/{userUid}")
    EmailDO queryByUserUid(@PathVariable("userUid") long userUid);

    @GetMapping("/message/email/{email}")
    EmailDO queryByEmail(@PathVariable("email") String email);
}*/
