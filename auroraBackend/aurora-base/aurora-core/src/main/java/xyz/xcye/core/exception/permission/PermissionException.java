package xyz.xcye.core.exception.permission;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/4 21:16
 */


public class PermissionException extends AuroraException {

    public PermissionException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public PermissionException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public PermissionException(String message) {
        super(message);
    }
}
