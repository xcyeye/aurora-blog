package xyz.xcye.admin.api.feign;

import org.springframework.stereotype.Component;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.core.entity.R;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@Component
public class ArticleFeignHandler implements ArticleFeignService {

    @Override
    public R insertArticle(ArticlePojo pojo) {
        return R.failure();
    }
}
