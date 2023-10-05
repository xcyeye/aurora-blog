package xyz.xcye.admin.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import xyz.xcye.admin.manager.task.LoadRolePermissionInfo;
import xyz.xcye.data.util.SpringUtil;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 这是角色权限部分的mybatis缓存
 *
 * @author qsyyke
 * @date Created in 2022/5/10 18:31
 */


public class RolePermissionMybatisCache implements Cache {
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    // 这里使用了redis缓存，使用springboot自动注入
    private RedisTemplate<String, Object> redisTemplate;

    private LoadRolePermissionInfo loadRolePermissionInfo;

    private String id;

    public RolePermissionMybatisCache(final String id) {
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
        if (redisTemplate == null) {
            // 由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        if (value != null) {
            redisTemplate.opsForValue().set(key.toString(), value);

            // 更新redis中的角色和权限的关系
            SpringUtil.getBean(LoadRolePermissionInfo.class).storagePermissionInfoToRedis(this.redisTemplate);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (redisTemplate == null) {
            // 由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        try {
            if (key != null) {
                return redisTemplate.opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (redisTemplate == null) {
            // 由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        if (key != null) {
            redisTemplate.delete(key.toString());
            // 更新redis中的角色和权限的关系
            SpringUtil.getBean(LoadRolePermissionInfo.class).storagePermissionInfoToRedis(this.redisTemplate);
        }
        return null;
    }

    @Override
    public void clear() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
            // 更新redis中的角色和权限的关系
            SpringUtil.getBean(LoadRolePermissionInfo.class).storagePermissionInfoToRedis(this.redisTemplate);
        }
    }

    @Override
    public int getSize() {
        if (redisTemplate == null) {
            // 由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        Long size = redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
