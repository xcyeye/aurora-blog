package xyz.xcye.common.exception;

import lombok.Data;

/**
 * 这是aurora全局异常父类 加入一些额外参数
 * @author qsyyke
 */

@Data
public class AuroraGlobalException extends Exception {
    /** 响应码 **/
    public Integer statusCode;

    public AuroraGlobalException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
