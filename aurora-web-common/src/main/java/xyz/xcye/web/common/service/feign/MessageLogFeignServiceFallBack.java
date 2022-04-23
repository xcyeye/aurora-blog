package xyz.xcye.web.common.service.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import xyz.xcye.common.dos.EmailDO;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.List;

@Component
@Slf4j
public class MessageLogFeignServiceFallBack implements MessageLogFeignService {
    @Override
    public ModifyResult insertMessageLog(MessageLogDO messageLogDO) throws BindException {
        log.error("发生异常了");
        return ModifyResult.operateResult("insertMessageLog发生意外了",200,200);
    }

    @Override
    public ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException {
        log.error("发生异常了");
        return ModifyResult.operateResult("updateMessageLog发生意外了",200,200);
    }

    @Override
    public ModifyResult deleteMessageLog(long uid) {
        log.error("发生异常了");
        return ModifyResult.operateResult("deleteMessageLog发生意外了",200,200);
    }

    @Override
    public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        log.error("发生异常了");
        return null;
    }

    @Override
    public MessageLogDO queryMessageLogByUid(long uid) {
        log.error("发生异常了");
        return null;
    }

    @Override
    public ModifyResult insertEmail(EmailDO email) {
        return null;
    }

    @Override
    public ModifyResult deleteEmailByUid(long uid) {
        return null;
    }

    @Override
    public ModifyResult updateDeleteStatus(EmailDO email) {
        return null;
    }

    @Override
    public ModifyResult updateEmailByUid(EmailDO email) {
        return null;
    }

    @Override
    public EmailDO queryByUid(long uid) {
        return null;
    }

    @Override
    public EmailDO queryByUserUid(long userUid) {
        return null;
    }

    @Override
    public EmailDO queryByEmail(String email) {
        return null;
    }
}