package xyz.xcye.message.controller;

import io.seata.core.context.RootContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.dos.MessageLogDO;
import xyz.xcye.common.dto.PaginationDTO;
import xyz.xcye.common.entity.result.ModifyResult;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.message.manager.schedule.RabbitMQSchedule;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.common.vo.MessageLogVO;

import javax.validation.groups.Default;
import java.util.List;

/**
 * @author qsyyke
 */

@Api(value = "消息中间件消费消息相关接口",tags = "消息中间件消费消息相关接口")
@RequestMapping("/message/messageLog")
@RestController
public class MessageLogController {
    @Autowired
    private MessageLogService messageLogService;

    @Autowired
    private RabbitMQSchedule rabbitMQSchedule;

    @ApiOperation(value = "插入新消费消息",notes = "插入新消费消息")
    @ResponseResult
    @PostMapping("")
    public ModifyResult insertMessageLog(@Validated({Insert.class,Default.class}) MessageLogDO messageLogDO) throws BindException {
        return messageLogService.insertMessageLog(messageLogDO);
    }

    @ApiOperation(value = "更新消费消息")
    @ResponseResult
    @PutMapping("")
    public ModifyResult updateMessageLog(@Validated(Update.class) MessageLogDO messageLogDO) throws BindException {
        return messageLogService.updateMessageLog(messageLogDO);
    }

    @ApiOperation(value = "删除消费消息")
    @ResponseResult
    @DeleteMapping("/delete/{uid}")
    public ModifyResult deleteMessageLog(@PathVariable("uid") long uid) {
        return messageLogService.deleteMessageLog(uid);
    }

    @ApiOperation(value = "查询所有消费消息")
    @ResponseResult
    @GetMapping("")
    public List<MessageLogVO> queryAllMessageLog(MessageLogDO messageLogDO, PaginationDTO paginationDTO) throws InstantiationException, IllegalAccessException {
        return messageLogService.queryAllMessageLog(messageLogDO,paginationDTO);
    }

    @ApiOperation(value = "根据uid查询消费消息")
    @ResponseResult
    @GetMapping("/{uid}")
    public MessageLogDO queryMessageLogByUid(@PathVariable("uid") long uid) {
        try {
            rabbitMQSchedule.reconsumeMQMessageTask();
        } catch (BindException e) {
            e.printStackTrace();
        }
        return messageLogService.queryByUid(uid);
    }
}
