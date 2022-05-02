package xyz.xcye.core.exception.file;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * 和文件相关的自定义异常
 * @author qsyyke
 */


public class FileException extends AuroraException {

    public FileException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public FileException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public FileException(String message) {
        super(message);
    }

}
