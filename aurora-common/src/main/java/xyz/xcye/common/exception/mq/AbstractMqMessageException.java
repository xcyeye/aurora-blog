package xyz.xcye.common.exception.mq;

import xyz.xcye.common.enums.ResponseStatusCodeEnum;
import xyz.xcye.common.exception.AuroraException;

/**
 * 和消息中间件相关的抽象异常类
 * @author qsyyke
 * @date Created in 2022/4/28 08:52
 */


public abstract class AbstractMqMessageException extends AuroraException {

    public AbstractMqMessageException(String message, Integer statusCode) {
        super(message, statusCode);
    }

    public AbstractMqMessageException(ResponseStatusCodeEnum responseCodeInfo) {
        super(responseCodeInfo);
    }

    public AbstractMqMessageException(String message) {
        super(message);
    }
}
