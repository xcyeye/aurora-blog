package xyz.xcye.oauth.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.admin.po.User;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.oauth.api.service.handler.UserFeignHandler;

/**
 * @author qsyyke
 * @date Created in 2022/5/7 12:51
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin",
        contextId = "authUserFeignServer", fallback = UserFeignHandler.class)
public interface UserFeignService {

    @PostMapping("/admin/user/queryUserByUsernameContainPassword")
    R queryUserByUsernameContainPassword(@SpringQueryMap String username);
}
