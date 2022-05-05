package xyz.xcye.core.exception.role;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:07
 */


public class RoleException extends AuroraException {

    public RoleException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public RoleException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public RoleException(String message) {
        super(message);
    }
}
