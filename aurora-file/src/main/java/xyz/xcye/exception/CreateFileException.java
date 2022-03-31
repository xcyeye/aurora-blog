package xyz.xcye.exception;

import lombok.Data;

import java.io.IOException;

/**
 * @author qsyyke
 */

@Data
public class CreateFileException extends IOException {
    /**
     * 创建文件发生的路径
     */
    private String path;

    public CreateFileException(String path) {
        this.path = path;
    }

    public CreateFileException(String message, String path) {
        super(message);
        this.path = path;
    }
}
