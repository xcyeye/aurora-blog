package xyz.xcye.core.exception.setting;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 */


public class SettingException extends AuroraException {

    public SettingException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public SettingException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public SettingException(String message) {
        super(message);
    }
}
