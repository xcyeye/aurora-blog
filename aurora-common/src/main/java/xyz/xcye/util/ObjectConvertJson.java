package xyz.xcye.util;

import com.alibaba.fastjson.JSON;

/**
 * 将一个对象转换成json格式的字符串工具类
 * @author qsyyke
 */


public class ObjectConvertJson {

    public static String jsonToString(Object data) throws Exception {
        String json = "";
        try {
            json = JSON.toJSONString(data);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return json;
    }
}
