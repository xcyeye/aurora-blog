package xyz.xcye.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.xcye.core.annotaion.controller.ModifyOperation;
import xyz.xcye.core.annotaion.controller.SelectOperation;
import xyz.xcye.core.valid.Insert;
import xyz.xcye.core.valid.Update;
import xyz.xcye.data.entity.Condition;
import xyz.xcye.data.entity.PageData;
import xyz.xcye.message.po.MessageLog;
import xyz.xcye.message.pojo.MessageLogPojo;
import xyz.xcye.message.service.MessageLogService;
import xyz.xcye.message.vo.MessageLogVO;

import javax.validation.groups.Default;

/**
 * @author qsyyke
 */

@Tag(name = "消息中间件消费消息相关接口")
@RequestMapping("/message/messageLog")
@RestController
@RefreshScope
public class MessageLogController {
    @Autowired
    private MessageLogService messageLogService;

    /*@Autowired
    private RabbitMQSchedule rabbitMQSchedule;*/

    @Operation(summary = "插入新消费消息")
    @ModifyOperation
    @PostMapping("/insertMessageLog")
    public void insertMessageLog(@Validated({Insert.class,Default.class}) @RequestBody MessageLogPojo messageLog)
            throws BindException {
        messageLogService.insertMessageLog(messageLog);
    }

    @Operation(summary = "更新消费消息")
    @ModifyOperation
    @PostMapping("/updateMessageLog")
    public int updateMessageLog(@Validated(Update.class) @RequestBody MessageLogPojo messageLog) throws BindException {
        return messageLogService.updateMessageLog(messageLog);
    }

    @Operation(summary = "删除消费消息")
    @ModifyOperation
    @PostMapping("/physicalDeleteMessageLog")
    public int physicalDeleteMessageLog(@RequestBody MessageLogPojo messageLog) {
        return messageLogService.physicalDeleteMessageLog(messageLog.getUid());
    }

    @Operation(summary = "查询所有消费消息")
    @SelectOperation
    @PostMapping("/queryListMessageLogByCondition")
    public PageData<MessageLogVO> queryListMessageLogByCondition(@RequestBody Condition<Long> condition) {
        return messageLogService.queryListMessageLogByCondition(condition);
    }

    @Operation(summary = "根据uid查询消费消息")
    @SelectOperation
    @PostMapping("/queryMessageLogByUid")
    public MessageLogVO queryMessageLogByUid(@RequestBody MessageLogPojo messageLog) {
        return messageLogService.queryMessageLogByUid(messageLog.getUid());
    }

    @Operation(summary = "重新投递此messageLogUid对应的mq消息")
    @SelectOperation
    @PostMapping("/resendRabbitMqMessage")
    public void resendRabbitMqMessage(@RequestBody MessageLogPojo messageLog) throws BindException {
        messageLogService.resendMqMessage(messageLog.getUid());
    }
}
