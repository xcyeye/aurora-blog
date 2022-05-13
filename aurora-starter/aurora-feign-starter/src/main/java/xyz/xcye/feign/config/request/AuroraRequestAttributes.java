package xyz.xcye.feign.config.request;

import org.springframework.web.context.request.RequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 10:42
 */


public class AuroraRequestAttributes implements RequestAttributes {

    private final Map<String, Object> map = new ConcurrentHashMap<>(1);

    @Override
    public Object getAttribute(String name, int scope) {
        return map.get(name);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        map.put(name, value);
    }

    @Override
    public void removeAttribute(String name, int scope) {
        map.remove(name);
    }

    @Override
    public String[] getAttributeNames(int scope) {
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {

    }

    @Override
    public Object resolveReference(String key) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }

    public List<String> getHeaders() {
        List<String> headList = new ArrayList<>();
        map.forEach((key,value) -> headList.add(key));
        return headList;
    }

    public Map<String,Object> getAllHeaderMap() {
        return map;
    }
}
