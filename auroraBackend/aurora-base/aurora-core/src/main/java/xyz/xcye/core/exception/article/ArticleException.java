package xyz.xcye.core.exception.article;

import xyz.xcye.core.enums.ResponseStatusCodeEnum;
import xyz.xcye.core.exception.AuroraException;

/**
 * @author qsyyke
 * @date Created in 2022/6/4 23:12
 */


public class ArticleException extends AuroraException {

    public ArticleException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public ArticleException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public ArticleException(String message) {
        super(message);
    }
}
