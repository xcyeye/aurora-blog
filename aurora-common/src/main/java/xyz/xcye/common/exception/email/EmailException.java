package xyz.xcye.common.exception.email;

import xyz.xcye.common.exception.AuroraCustomException;

/**
 * @author qsyyke
 */


public class EmailException extends AuroraCustomException {

    public EmailException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
