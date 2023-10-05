package xyz.xcye.oauth.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;
import xyz.xcye.oauth.api.service.handler.UserFeignHandler;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 12:51
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin",
        contextId = "authUserFeignServer", fallback = UserFeignHandler.class)
public interface UserFeignService {

    @PostMapping(value = "/admin/user/queryUserByUsernameContainPassword", headers = "content-type=application/json;charset=UTF-8;")
        // @RequestMapping
    R queryUserByUsernameContainPassword(@RequestBody UserPojo pojo);
}
