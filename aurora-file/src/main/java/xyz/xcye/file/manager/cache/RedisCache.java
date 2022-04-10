package xyz.xcye.file.manager.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.stereotype.Component;

/**
 * @author qsyyke
 */

@Slf4j
public class RedisCache implements Cache {

    private String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        log.error("34");
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.error("34");
    }

    @Override
    public Object getObject(Object key) {
        log.error("34");
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        log.error("34");
        return null;
    }

    @Override
    public void clear() {
        log.error("34");
    }

    @Override
    public int getSize() {
        log.error("34");
        return 0;
    }
}
