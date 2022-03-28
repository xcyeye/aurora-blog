package xyz.xcye.exception;

import lombok.Data;

/**
 * 这是aurora全局异常父类 加入一些额外参数
 * @author qsyyke
 */

@Data
public class AuroraGlobalException extends Exception {
    /** 响应码 **/
    public Integer statusCode;

    /** 发生异常的path **/
    public String path;

    public AuroraGlobalException(Integer statusCode,String path,String message) {
        super(message);
        this.statusCode = statusCode;
        this.path = path;
    }

    public AuroraGlobalException() {
        super();
    }

    public AuroraGlobalException(String message) {
        super(message);
    }

    public AuroraGlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuroraGlobalException(Throwable cause) {
        super(cause);
    }

    protected AuroraGlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
