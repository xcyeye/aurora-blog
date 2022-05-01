package xyz.xcye.aurora.feign.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.aurora.feign.MessageLogFeignService;
import xyz.xcye.common.entity.result.R;
import xyz.xcye.common.entity.table.MessageLogDO;

/**
 * @author qsyyke
 * @date Created in 2022/5/1 13:13
 */

@Component
@Slf4j
public class MessageLogFeignHandler implements MessageLogFeignService {

    @Override
    public R insertMessageLog(MessageLogDO messageLogDO) throws BindException {
        log.info("insertMessageLog");
        return null;
    }

    @Override
    public R updateMessageLog(MessageLogDO messageLogDO) throws BindException {
        log.info("updateMessageLog");
        return null;
    }

    @Override
    public R queryMessageLogByUid(long uid) {
        log.info("queryMessageLogByUid");
        return null;
    }
}
