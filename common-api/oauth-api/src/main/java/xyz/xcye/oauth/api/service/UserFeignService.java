package xyz.xcye.oauth.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 12:51
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin", contextId = "authUserFeignServer")
public interface UserFeignService {

    @GetMapping("/admin/user/{uid}")
    R queryUserByUid(@PathVariable("uid") long uid);

    @GetMapping("/admin/user")
    R insertUser(@SpringQueryMap Condition<Long> condition);

    @GetMapping("/admin/user/{username}")
    R queryUserByUsername(@PathVariable("username") String username);

    @GetMapping("/admin/user/pwd/{username}")
    R queryUserByUsernameContainPassword(@PathVariable("username") String username);
}
