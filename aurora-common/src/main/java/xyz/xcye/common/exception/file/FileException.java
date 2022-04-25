package xyz.xcye.common.exception.file;

import xyz.xcye.common.exception.AuroraCustomException;

import java.io.IOException;

/**
 * 和文件相关的自定义异常
 * @author qsyyke
 */


public class FileException extends AuroraCustomException {

    public FileException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
