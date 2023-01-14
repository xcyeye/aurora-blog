package xyz.xcye.article.api.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.message.po.Email;

/**
 * @author qsyyke
 */

@Component
public class ArticleUserFeignHandler implements ArticleUserFeignService {

    @Override
    public R queryUserByUid(UserPojo pojo) {
        return R.failure();
    }
}