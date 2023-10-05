package xyz.xcye.feign.config.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.core.entity.R;
import xyz.xcye.message.pojo.MessageLogPojo;

/**
 * @author qsyyke
 * @date Created in 2022/5/2 17:56
 */

@Component
public class MessageLogFeignHandler implements MessageLogFeignService {

    @Override
    public R insertMessageLog(MessageLogPojo messageLog) throws BindException {
        return R.failure();
    }

    @Override
    public R updateMessageLog(MessageLogPojo messageLog) throws BindException {
        return R.failure();
    }

    @Override
    public R queryMessageLogByUid(MessageLogPojo messageLog) {
        return R.failure();
    }
}
