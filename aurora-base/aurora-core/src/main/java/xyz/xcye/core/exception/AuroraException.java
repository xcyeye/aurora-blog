package xyz.xcye.core.exception;

import lombok.Data;
import xyz.xcye.core.enums.ResponseStatusCodeEnum;

/**
 * 这是aurora全局异常父类 加入一些额外参数
 * @author qsyyke
 */

@Data
public class AuroraException extends RuntimeException {
    /** 响应码 **/
    public Integer statusCode;

    public AuroraException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public AuroraException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo.getMessage());
        this.statusCode = responseCodeInfo.getCode();
    }

    public AuroraException(String message) {
        super(message);
        this.statusCode = ResponseStatusCodeEnum.UNKNOWN.getCode();
    }
}
