package xyz.xcye.common.entity.result;

import lombok.Builder;
import lombok.Data;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;

import java.util.HashMap;


/**
 * 统一返回体
 * @author qsyyke
 */

@Builder
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
        return success(ResponseStatusCodeEnum.SUCCESS.getCode(),
                ResponseStatusCodeEnum.SUCCESS.getMessage(),new HashMap<>());
    }

    /**
     * 成功 不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    public static R success(Integer code,String message) {
        return success(code,message,new HashMap<>());
    }

    /**
     * 成功，携带需要返回的数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 需要返回的数据，不需要传入map对象，会自动放在data里
     * @return R对象
     */
    public static R success(Integer code,String message,Object data) {
        if (data == null) {
            data = new HashMap<>();
        }
        return R.builder().code(code).message(message).data(data).build();
    }

    /**
     * 失败，不携带任何数据
     * @return R对象
     */
    public static R failure() {
        return failure(ResponseStatusCodeEnum.UNKNOWN.getCode(),
                ResponseStatusCodeEnum.UNKNOWN.getMessage(),new HashMap<>());
    }

    /**
     * 失败，不携带任何数据
     * @param code 响应状态码
     * @param message 消息
     * @return R对象
     */
    public static R failure(Integer code,String message) {
        return failure(code,message,new HashMap<>());
    }

    /**
     * 失败 携带数据
     * @param code 响应状态码
     * @param message 消息
     * @param data 数据
     * @return R对象
     */
    public static R failure(Integer code,String message,Object data) {
        if (data == null) {
            data = new HashMap<>();
        }
        return R.builder().code(code).message(message).data(data).build();
    }
}
