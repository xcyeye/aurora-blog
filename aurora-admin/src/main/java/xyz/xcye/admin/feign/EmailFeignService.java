package xyz.xcye.admin.feign;

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
