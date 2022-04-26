package xyz.xcye.common.exception.user;

import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.AuroraGlobalException;

/**
 * @author qsyyke
 */


public class UserException extends AuroraGlobalException {

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
