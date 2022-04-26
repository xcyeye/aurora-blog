package xyz.xcye.common.exception.email;

import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.AuroraGlobalException;

/**
 * @author qsyyke
 */


public class EmailException extends AuroraGlobalException {

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
