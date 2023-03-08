package xyz.xcye.core.exception.comment;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/5/12 14:59
 */


public class CommentException extends AuroraException {

    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public CommentException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }
}