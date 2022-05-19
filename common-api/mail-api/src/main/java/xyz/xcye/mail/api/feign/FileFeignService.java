package xyz.xcye.mail.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.mail.api.feign.handler.FileFeignHandler;

/**
 * @author qsyyke
 * @date Created in 2022/5/18 19:29
 */

@FeignClient(value = "aurora-file", name = "aurora-file", fallback = FileFeignHandler.class)
public interface FileFeignService {

    @GetMapping("/file")
    R queryAllFile(@SpringQueryMap Condition<Long> condition);
}
