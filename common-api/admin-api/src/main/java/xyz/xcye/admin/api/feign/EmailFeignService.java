package xyz.xcye.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.po.Email;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@FeignClient(value = "aurora-message", contextId = "admin-aurora-email", fallback = EmailFeignHandler.class)
public interface EmailFeignService {
    
    @GetMapping("/message/email/queryByEmailNumber")
    R queryByEmailNumber(@SpringQueryMap String email);
}
