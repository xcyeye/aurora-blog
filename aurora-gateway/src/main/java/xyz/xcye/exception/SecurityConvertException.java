package xyz.xcye.exception;


/**
 * @author qsyyke
 */

public class SecurityConvertException extends AuroraGlobalException {
    public SecurityConvertException(Integer statusCode, String path, String message) {
        super(statusCode, path, message);
    }
}
