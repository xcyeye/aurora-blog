package xyz.xcye.common.entity.table;

import lombok.Data;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;

/**
 * 消息中间件消费消息的日志
 * @author qsyyke
 */

@Data
public class MessageLogDO {
    /**
     * 唯一uid
     */
    @NotNull(message = "消息投递日志uid不能为null", groups={Update.class, Delete.class})
    private Long uid;

    /**
     * 生产者生产的消息
     */
    @ValidateString(value = "消息投递日志内容", groups={Insert.class},max = 30000)
    private String message;

    /**
     * 如果发生错误，则错误消息是什么
     */
    private String errorMessage;

    /**
     * 交换机
     */
    @ValidateString(value = "消息投递日志的交换机", groups={Insert.class})
    private String exchange;

    /**
     * 队列
     */
    private String queue;

    /**
     * 绑定路由key
     */
    @ValidateString(value = "消息投递日志的绑定路由key", groups={Insert.class})
    private String routingKey;

    /**
     * 重试次数
     */
    @NotNull(message = "消息投递日志的重试次数不能为null", groups={Insert.class,Update.class})
    private Integer tryCount;

    /**
     * 消息消费的状态，true
     */
    @NotNull(message = "消息投递日志的发送状态不能为null", groups={Insert.class,Update.class})
    private Boolean consumeStatus;

    @NotNull(groups = {Insert.class},message = "消息的应答状态")
    private Boolean ackStatus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 交换机类型
     */
    @ValidateString(value = "消息投递日志的交换机类型", groups={Insert.class})
    private String exchangeType;
}
