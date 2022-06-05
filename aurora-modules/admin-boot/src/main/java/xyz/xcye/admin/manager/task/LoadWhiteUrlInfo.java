package xyz.xcye.admin.manager.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.xcye.admin.constant.RedisStorageConstant;
import xyz.xcye.admin.po.WhiteUrl;
import xyz.xcye.admin.service.WhiteUrlService;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.data.entity.Condition;

import java.time.Duration;
import java.util.List;

/**
 * 将mysql中的白名单数据存入redis中
 * @author qsyyke
 * @date Created in 2022/5/7 18:22
 */

@Slf4j
@Component
public class LoadWhiteUrlInfo {
    @Autowired
    private WhiteUrlService whiteUrlService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void storageWhiteUrlInfoToRedis(RedisTemplate<String, Object> template) {
        storageData(template);
    }

    public void storageWhiteUrlInfoToRedis() {
        storageData(this.redisTemplate);
    }

    private void storageData(RedisTemplate<String, Object> template) {
        Condition<Integer> condition = new Condition<>();
        condition.setPageNum(0);
        condition.setPageSize(100000000);
        // 获取所有的角色权限关系
        List<WhiteUrl> whiteUrlList = whiteUrlService.selectWhiteUrlByCondition(condition).getResult();
        // 存入redis中Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 3) * 60)
        template.opsForValue().set(RedisStorageConstant.STORAGE_WHITE_URL_INFO, whiteUrlList,
                Duration.ofSeconds(DateUtils.getRandomMinute(60, 60 * 24 * 3) * 60));
    }
}
