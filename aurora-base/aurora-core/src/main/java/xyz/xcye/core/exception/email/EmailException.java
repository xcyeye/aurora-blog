package xyz.xcye.core.exception.email;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 */


public class EmailException extends AuroraException {

    public EmailException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public EmailException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public EmailException(String message) {
        super(message);
    }

}
