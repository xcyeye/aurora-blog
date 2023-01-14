package xyz.xcye.core.exception.feign;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/18 16:32
 */


public class FeignException extends AuroraException {

    public FeignException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public FeignException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public FeignException(String message) {
        super(message);
    }
}
