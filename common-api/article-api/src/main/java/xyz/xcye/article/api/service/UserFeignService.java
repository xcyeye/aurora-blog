package xyz.xcye.article.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.xcye.core.entity.R;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 19:13
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin")
public interface UserFeignService {

    @GetMapping("/admin/user/{uid}")
    R queryUserByUid(@PathVariable("uid") long uid);
}
