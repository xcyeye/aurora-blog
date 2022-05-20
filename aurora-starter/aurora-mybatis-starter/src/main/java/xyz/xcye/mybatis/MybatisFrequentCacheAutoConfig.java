package xyz.xcye.mybatis;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import xyz.xcye.core.util.DateUtils;
import xyz.xcye.data.util.SpringUtil;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 这是一个用于mybatis的redis二级缓存
 * @author qsyyke
 * @date Created in 2022/5/2 13:11
 */

public class MybatisFrequentCacheAutoConfig implements Cache {
    /**
     * 读写锁
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    /**
     * 最大失效时间为5小时
     */
    private final int maxExpiredMinute = 300;

    /**
     * 最小失效时间为30分钟
     */
    private final int minExpiredMinute = 30;

    //这里使用了redis缓存，使用springboot自动注入
    private RedisTemplate<String, Object> redisTemplate;

    private final String id;

    public MybatisFrequentCacheAutoConfig(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        getRedisTemplate(redisTemplate);
        if (value != null) {
            redisTemplate.opsForValue().set(key.toString(), value, getExpiredMinute());
        }
    }

    @Override
    public Object getObject(Object key) {
        getRedisTemplate(redisTemplate);
        if (key != null) {
            return redisTemplate.opsForValue().get(key.toString());
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        getRedisTemplate(redisTemplate);
        if (key != null) {
            redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        getRedisTemplate(redisTemplate);
        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public int getSize() {
        getRedisTemplate(redisTemplate);
        Long size = redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        if (size == null) {
            size = 0L;
        }
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    private Duration getExpiredMinute() {
        return Duration.ofSeconds(DateUtils.getRandomMinute(minExpiredMinute, maxExpiredMinute) * 60);
    }

    private void getRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
    }
}
