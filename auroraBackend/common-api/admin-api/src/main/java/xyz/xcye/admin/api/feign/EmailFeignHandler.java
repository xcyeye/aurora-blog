package xyz.xcye.admin.api.feign;

import org.springframework.stereotype.Component;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.pojo.EmailPojo;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@Component
public class EmailFeignHandler implements EmailFeignService {

    @Override
    public R queryByEmailNumber(EmailPojo pojo) {
        return R.failure();
    }

    @Override
    public R queryEmailByUid(EmailPojo pojo) {
        return R.failure();
    }

    @Override
    public R insertEmail(EmailPojo pojo) {
        return R.failure();
    }
}
