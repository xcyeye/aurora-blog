package xyz.xcye.article.api.service;

import org.springframework.stereotype.Component;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;

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