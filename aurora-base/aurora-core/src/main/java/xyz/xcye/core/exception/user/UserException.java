package xyz.xcye.core.exception.user;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 */


public class UserException extends AuroraException {

    public UserException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public UserException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public UserException(String message) {
        super(message);
    }
}
