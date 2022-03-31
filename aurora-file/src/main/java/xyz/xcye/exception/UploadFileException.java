package xyz.xcye.exception;

import java.io.IOException;

/**
 * @author qsyyke
 */


public class UploadFileException extends IOException {
    /**
     * 创建文件发生的路径
     */
    private String path;

    public UploadFileException(String path) {
        this.path = path;
    }

    public UploadFileException(String message, String path) {
        super(message);
        this.path = path;
    }
}
