package xyz.xcye.message.service;

import org.springframework.validation.BindException;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.vo.MessageLogVO;

import java.util.List;

/**
 * 操作消息中间件的消费消息日志
 * @author qsyyke
 */

public interface MessageLogService {
    ModifyResult insertMessageLog(MessageLogDO messageLogDO,String xid) throws BindException;
    ModifyResult deleteMessageLog(long uid);
    ModifyResult updateMessageLog(MessageLogDO messageLogDO) throws BindException;
    List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException;
    MessageLogDO queryByUid(long uid);

    String seataTest(String xid) throws BindException;
}
