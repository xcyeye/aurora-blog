package xyz.xcye.comment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.xcye.core.entity.R;

/**
 * @author qsyyke
 * @date Created in 2022/5/11 13:19
 */

@FeignClient(value = "aurora-admin",name = "aurora-admin")
public interface UserFeignService {

    @GetMapping("/admin/role/{uid}")
    R queryRoleByUid(@PathVariable("uid") int uid);
}
