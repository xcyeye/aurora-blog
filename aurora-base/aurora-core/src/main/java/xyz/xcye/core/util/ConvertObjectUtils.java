package xyz.xcye.core.util;

import com.alibaba.fastjson.JSON;

/**
 * 将一个对象转换成json格式的字符串工具类
 * @author qsyyke
 */


public class ConvertObjectUtils {

    public static String jsonToString(Object data) {
        String json = "";
        try {
            json = JSON.toJSONString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}
