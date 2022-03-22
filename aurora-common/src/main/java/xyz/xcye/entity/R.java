package xyz.xcye.entity;

import lombok.Builder;
import xyz.xcye.enums.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回体
 * @author qsyyke
 */

@Builder
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
    private static R success() {
        return R.builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }

    /**
     * 成功 不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    private static R success(Integer code,String message) {
        return R.builder()
                .code(code).message(message).build();
    }

    /**
     * 成功，携带需要返回的数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 需要返回的数据，不需要传入map对象，会自动放在data里
     * @return R对象
     */
    private static R success(Integer code,String message,Object data) {
        return R.builder()
                .code(code).message(message)
                .data(getDataMap(data)).build();
    }

    /**
     * 失败，不携带任何数据
     * @return R对象
     */
    private static R failure() {
        return R.builder()
                .code(ResultCode.UNKNOWN.getCode())
                .message(ResultCode.UNKNOWN.getMessage())
                .build();
    }

    /**
     * 失败，不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    private static R failure(Integer code,String message) {
        return R.builder()
                .code(code).message(message).build();
    }

    /**
     * 失败 携带数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 数据
     * @return R对象
     */
    private static R failure(Integer code,String message,Object data) {
        return R.builder()
                .code(code).message(message)
                .data(getDataMap(data)).build();
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
    private static Map<String,Object> getDataMap(Object data) {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("data",data);
        return dataMap;
    }

    private R() {}
}
