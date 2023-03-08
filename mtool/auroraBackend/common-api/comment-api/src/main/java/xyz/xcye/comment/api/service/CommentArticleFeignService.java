package xyz.xcye.comment.api.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author qsyyke
 * @date Created in 2022/5/12 08:14
 */

@FeignClient(value = "aurora-article", name = "aurora-article")
public interface CommentArticleFeignService {

}
