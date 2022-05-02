package xyz.xcye.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author qsyyke
 */


public class JSONUtils {
    /**
     * 从R返回对象中，解析出key对应的数据
     * @param json
     * @param key
     * @param target
     * @param <T>
     * @return
     */
    public static  <T> T parseObjFromResult(String json, String key, Class<T> target) {
        JSONObject jsonObject = JSON.parseObject(json);
        T t = null;
        try {
            t = JSON.parseObject(jsonObject.getString(key), target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
