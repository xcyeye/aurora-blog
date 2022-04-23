package xyz.xcye.common.exception.user;

import xyz.xcye.common.exception.AuroraCustomException;

/**
 * @author qsyyke
 */


public class UserException extends AuroraCustomException {

    public UserException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
