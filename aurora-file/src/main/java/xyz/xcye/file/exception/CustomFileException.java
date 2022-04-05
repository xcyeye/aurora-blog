package xyz.xcye.file.exception;

import lombok.Data;

import java.io.IOException;


/**
 * @author qsyyke
 */

@Data
public class CustomFileException extends IOException {
    private int code;

    public CustomFileException(int code) {
        this.code = code;
    }

    public CustomFileException(String message, int code) {
        super(message);
        this.code = code;
    }

    public CustomFileException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public CustomFileException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
