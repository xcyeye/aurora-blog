package xyz.xcye.message.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.vo.MessageLogVO;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@Api(value = "消息中间件消费消息相关接口",tags = "消息中间件消费消息相关接口")
@RequestMapping("/message/messageLog")
@RestController
public class MessageLogController {
    @Autowired
    private MessageLogService messageLogService;

    /*@Autowired
    private RabbitMQSchedule rabbitMQSchedule;*/

    @ApiOperation(value = "插入新消费消息",notes = "插入新消费消息")
    @ModifyOperation
    @PostMapping("")
    public int insertMessageLog(@Validated({Insert.class,Default.class}) @RequestBody MessageLog messageLog)
            throws BindException {
        return messageLogService.insertMessageLog(messageLog);
    }

    @ApiOperation(value = "更新消费消息")
    @ModifyOperation
    @PutMapping("")
    public int updateMessageLog(@Validated(Update.class) @RequestBody MessageLog messageLog) throws BindException {
        return messageLogService.updateMessageLog(messageLog);
    }

    @ApiOperation(value = "删除消费消息")
    @ModifyOperation
    @DeleteMapping("/{uid}")
    public int deleteMessageLog(@PathVariable("uid") long uid) {
        return messageLogService.deleteMessageLog(uid);
    }

    @ApiOperation(value = "查询所有消费消息")
    @SelectOperation
    @GetMapping("")
    public PageData<MessageLogVO> queryAllMessageLog(Condition<Long> condition) {
        return messageLogService.queryAllMessageLog(condition);
    }

    @ApiOperation(value = "根据uid查询消费消息")
    @SelectOperation
    @GetMapping("/{uid}")
    public MessageLogVO queryMessageLogByUid(@PathVariable("uid") long uid) {
        return messageLogService.queryByUid(uid);
    }
}
