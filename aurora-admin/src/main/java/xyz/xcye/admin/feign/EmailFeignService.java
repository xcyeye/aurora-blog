package xyz.xcye.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.entity.table.EmailDO;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@FeignClient(value = "aurora-message", contextId = "admin-aurora-email")
@Component
public interface EmailFeignService {
    
    @DeleteMapping("/message/email/{uid}")
    R deleteEmailByUid(@PathVariable(value = "uid") long uid);
    
    @PutMapping("/message/email")
    R updateEmailByUid(@SpringQueryMap EmailDO email);
    
    @GetMapping("/message/email/{uid}")
    R queryByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException;
    
    @GetMapping("/message/email/userUid/{userUid}")
    R queryByUserUid(@PathVariable("userUid") long userUid) throws ReflectiveOperationException;
    
    @GetMapping("/message/email/email/{email}")
    R queryByEmail(@PathVariable("email") String email) throws ReflectiveOperationException;
}
