package xyz.xcye.core.exception.link;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/13 19:46
 */


public class LinkException extends AuroraException {

    public LinkException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public LinkException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public LinkException(String message) {
        super(message);
    }
}
