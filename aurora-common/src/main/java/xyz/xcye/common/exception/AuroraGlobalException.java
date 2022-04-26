package xyz.xcye.common.exception;

import lombok.Data;
import xyz.xcye.common.enums.ResponseStatusCodeEnum;

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

    public AuroraGlobalException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo.getMessage());
        this.statusCode = responseCodeInfo.getCode();
    }

    public AuroraGlobalException(String message) {
        super(message);
        this.statusCode = ResponseStatusCodeEnum.UNKNOWN.getCode();
    }
}
