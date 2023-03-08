package xyz.xcye.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.pojo.EmailPojo;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@FeignClient(value = "aurora-message", contextId = "admin-aurora-email", fallback = EmailFeignHandler.class)
public interface EmailFeignService {
    
    @PostMapping("/message/email/queryByEmailNumber")
    R queryByEmailNumber(@RequestBody EmailPojo pojo);

    @PostMapping("/message/email/queryEmailByUid")
    R queryEmailByUid(@RequestBody EmailPojo pojo);

    @PostMapping("/message/email/insertEmail")
    R insertEmail(@RequestBody EmailPojo pojo);
}
