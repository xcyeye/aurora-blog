package xyz.xcye.oauth.api.service.handler;

import org.springframework.stereotype.Component;
import xyz.xcye.admin.po.User;
import xyz.xcye.core.entity.R;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.oauth.api.service.UserFeignService;

/**
 * @author qsyyke
 * @date Created in 2022/5/18 12:40
 */

@Component
public class UserFeignHandler implements UserFeignService {

    @Override
    public R queryUserByUid(long uid) {
        return R.failure();
    }

    @Override
    public R insertUser(Condition<Long> condition) {
        return R.failure();
    }

    @Override
    public R updateUser(User user) {
        return R.failure();
    }

    @Override
    public R queryUserByUsername(String username) {
        return R.failure();
    }

    @Override
    public R queryUserByUsernameContainPassword(String username) {
        return R.failure();
    }
}
