package xyz.xcye.admin.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.xcye.article.pojo.ArticlePojo;
import xyz.xcye.core.entity.R;

/**
 * @author qsyyke
 * @date Created in 2022/4/30 15:12
 */

@FeignClient(value = "aurora-article", contextId = "admin-aurora-article", fallback = ArticleFeignHandler.class)
public interface ArticleFeignService {

    @PostMapping("/blog/article/insertArticle")
    R insertArticle (@RequestBody ArticlePojo pojo);
}
