package xyz.xcye.exception;

/**
 * @author qsyyke
 */

public class SecurityAccountException extends AuroraGlobalException {
    public SecurityAccountException(Integer statusCode, String path, String message) {
        super(statusCode, path, message);
        System.out.println(statusCode);
        System.out.println(message);
        System.out.println(path);
        System.out.println("----------------");
    }
}
