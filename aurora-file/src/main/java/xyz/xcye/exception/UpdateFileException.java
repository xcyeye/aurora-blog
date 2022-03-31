package xyz.xcye.exception;

import java.io.IOException;

/**
 * @author qsyyke
 */


public class UpdateFileException extends IOException {
    /**
     * 创建文件发生的路径
     */
    private String path;

    public UpdateFileException(String path) {
        this.path = path;
    }

    public UpdateFileException(String message, String path) {
        super(message);
        this.path = path;
    }
}
