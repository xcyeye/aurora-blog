package xyz.xcye.core.exception.token;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/15 14:37
 */


public class TokenException extends AuroraException {

    public TokenException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public TokenException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public TokenException(String message) {
        super(message);
    }
}
