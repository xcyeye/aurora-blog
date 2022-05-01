package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.common.annotaion.ResponseResult;
import xyz.xcye.common.entity.table.MessageLogDO;
import xyz.xcye.common.dto.ConditionDTO;
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
    public ModifyResult insertMessageLog(@Validated({Insert.class,Default.class}) MessageLogDO messageLogDO)
            throws BindException {
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
    @DeleteMapping("/{uid}")
    public ModifyResult deleteMessageLog(@PathVariable("uid") long uid) {
        return messageLogService.deleteMessageLog(uid);
    }

    @ApiOperation(value = "查询所有消费消息")
    @ResponseResult
    @GetMapping("")
    public List<MessageLogVO> queryAllMessageLog(ConditionDTO<Long> condition) throws ReflectiveOperationException {
        return messageLogService.queryAllMessageLog(condition);
    }

    @ApiOperation(value = "根据uid查询消费消息")
    @ResponseResult
    @GetMapping("/{uid}")
    public MessageLogVO queryMessageLogByUid(@PathVariable("uid") long uid) throws ReflectiveOperationException {
        return messageLogService.queryByUid(uid);
    }
}
