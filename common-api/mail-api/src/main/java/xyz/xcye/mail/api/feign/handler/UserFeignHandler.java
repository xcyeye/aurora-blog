package xyz.xcye.mail.api.feign.handler;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.ModifyResult;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.mail.api.feign.UserFeignService;
import xyz.xcye.message.po.Email;

/**
 * @author qsyyke
 */

@Component
public class UserFeignHandler implements UserFeignService {

    @Override
    public R queryUserByUid(long uid) {
        return R.failure();
    }

    @Override
    public ModifyResult updateUser(UserPojo user) throws UserException {
        return null;
    }

    @Override
    public R bindingEmail(String email) throws BindException {
        return R.failure();
    }
}