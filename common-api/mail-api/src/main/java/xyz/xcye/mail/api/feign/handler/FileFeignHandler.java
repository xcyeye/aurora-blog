package xyz.xcye.mail.api.feign.handler;

import org.springframework.stereotype.Component;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.mail.api.feign.FileFeignService;

/**
 * @author qsyyke
 * @date Created in 2022/5/18 19:33
 */

@Component
public class FileFeignHandler implements FileFeignService {

    @Override
    public R queryListFileByCondition(Condition<Long> condition) {
        return R.failure();
    }
}
