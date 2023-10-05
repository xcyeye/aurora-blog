package xyz.xcye.article.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;

/**
 * @author qsyyke
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin", contextId = "article-user-feign", fallback = ArticleUserFeignHandler.class)
public interface ArticleUserFeignService {

    @PostMapping("/admin/user/queryUserByUid")
    R queryUserByUid(@RequestBody UserPojo pojo);
}