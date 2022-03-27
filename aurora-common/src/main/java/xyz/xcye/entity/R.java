package xyz.xcye.entity;

import lombok.Data;
import xyz.xcye.enums.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回体
 * @author qsyyke
 */

@Data
public class R {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    /**
     * 成功，不携带任何数据
     * @return R对象
     */
    public static R success() {
        return new R(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功 不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    public static R success(Integer code,String message) {
        return new R(code,message);
    }

    /**
     * 成功，携带需要返回的数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 需要返回的数据，不需要传入map对象，会自动放在data里
     * @return R对象
     */
    public static R success(Integer code,String message,Object data) {
        return new R(code,message,data);
    }

    /**
     * 失败，不携带任何数据
     * @return R对象
     */
    public static R failure() {
        return new R(ResultCode.UNKNOWN.getCode(),ResultCode.UNKNOWN.getMessage());
    }

    /**
     * 失败，不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    public static R failure(Integer code,String message) {
        return new R(code,message);
    }

    /**
     * 失败 携带数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 数据
     * @return R对象
     */
    public static R failure(Integer code,String message,Object data) {
        return new R(code,message,data);
    }

    private R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private R(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 根据传入的Object对象，返回一个map集合
     * @param data Object对象
     * @return {"data",Object对象}
     */
   /* private static Map<String,Object> getDataMap(Object data) {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",data);
        return dataMap;
    }*/

    private R() {}
}
