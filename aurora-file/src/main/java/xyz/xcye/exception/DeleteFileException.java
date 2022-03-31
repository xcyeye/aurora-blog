package xyz.xcye.exception;

import java.io.IOException;

/**
 * @author qsyyke
 */


public class DeleteFileException extends IOException {
    /**
     * 创建文件发生的路径
     */
    private String path;

    public DeleteFileException(String path) {
        this.path = path;
    }

    public DeleteFileException(String message, String path) {
        super(message);
        this.path = path;
    }
}
