package xyz.xcye.exception;

/**
 * @author qsyyke
 */

public class SecurityAuthenticationException extends AuroraGlobalException {
    public SecurityAuthenticationException(Integer statusCode, String path, String message) {
        super(statusCode, path, message);
    }
}
