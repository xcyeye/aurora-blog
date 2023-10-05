package xyz.xcye.mail.api.feign.handler;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.mail.api.feign.UserFeignService;

/**
 * @author qsyyke
 */

@Component
public class UserFeignHandler implements UserFeignService {

    @Override
    public R queryUserByUid(UserPojo pojo) {
        return R.failure();
    }

    @Override
    public R updateUser(UserPojo pojo) throws UserException {
        return null;
    }

    @Override
    public R bindingEmail(UserPojo pojo) throws BindException {
        return R.failure();
    }
}