package xyz.xcye.core.exception.common;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/16 20:12
 */


public class CommonException extends AuroraException {

    public CommonException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public CommonException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public CommonException(String message) {
        super(message);
    }
}
