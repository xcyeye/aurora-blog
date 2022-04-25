package xyz.xcye.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.entity.table.EmailDO;
import xyz.xcye.common.entity.table.UserDO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.exception.user.UserException;

/**
 * @author qsyyke
 */

@FeignClient(value = "aurora-admin")
public interface UserFeignService {

    @GetMapping("/admin/user/{uid}")
    R queryUserByUid(@PathVariable("uid") long uid) throws InstantiationException, IllegalAccessException;

    @PutMapping("/admin/user")
    ModifyResult updateUser(@SpringQueryMap UserDO userDO) throws UserException;

    @PutMapping("/admin/user/bindingEmail")
    R bindingEmail(@SpringQueryMap EmailDO emailDO) throws BindException;
}
