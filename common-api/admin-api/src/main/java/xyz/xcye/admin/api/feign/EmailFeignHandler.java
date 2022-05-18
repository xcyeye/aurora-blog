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
    public R deleteEmailByUid(long uid) {
        return R.failure();
    }

    @Override
    public R updateEmailByUid(Email email) {
        return R.failure();
    }

    @Override
    public R queryByUid(long uid) {
        return R.failure();
    }

    @Override
    public R queryByUserUid(long userUid) {
        return R.failure();
    }

    @Override
    public R queryByEmail(String email) {
        return R.failure();
    }
}
