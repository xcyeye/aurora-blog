package xyz.xcye.article.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.admin.po.User;
import xyz.xcye.admin.pojo.UserPojo;
import xyz.xcye.core.entity.R;
import xyz.xcye.core.exception.user.UserException;
import xyz.xcye.message.po.Email;

/**
 * @author qsyyke
 */

@FeignClient(value = "aurora-admin", name = "aurora-admin", contextId = "article-user-feign", fallback = ArticleUserFeignHandler.class)
public interface ArticleUserFeignService {

    @PostMapping("/admin/user/queryUserByUid")
    R queryUserByUid(@RequestBody UserPojo pojo);
}