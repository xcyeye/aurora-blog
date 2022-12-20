package xyz.xcye.admin.api.feign;

import org.springframework.stereotype.Component;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.po.Email;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@Component
public class EmailFeignHandler implements EmailFeignService {

    @Override
    public R queryByEmailNumber(String email) {
        return R.failure();
    }
}
