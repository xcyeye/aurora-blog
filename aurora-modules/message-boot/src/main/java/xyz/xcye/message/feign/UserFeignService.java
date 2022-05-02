package xyz.xcye.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.message.po.Email;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.core.entity.result.ModifyResult;
import xyz.xcye.core.entity.result.R;
import xyz.xcye.core.exception.user.UserException;

/**
 * @author qsyyke
 */

@FeignClient(value = "aurora-admin")
public interface UserFeignService {

    @GetMapping("/admin/user/{uid}")
    R queryUserByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException;

    @PutMapping("/admin/user")
    ModifyResult updateUser(@SpringQueryMap UserDO userDO) throws UserException;

    @PutMapping("/admin/user/bindingEmail")
    R bindingEmail(@SpringQueryMap Email email) throws BindException;
}
