package xyz.xcye.admin.exception;

import xyz.xcye.common.exception.AuroraGlobalException;

/**
 * @author qsyyke
 */


public class UserException extends AuroraGlobalException {

    public UserException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
