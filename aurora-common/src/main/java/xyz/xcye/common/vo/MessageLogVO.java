package xyz.xcye.common.vo;

import lombok.Data;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Insert;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

/**
 * @author qsyyke
 */

@Data
public class MessageLogVO {
    /**
     * 唯一uid
     */
    private Long uid;

    /**
     * 生产者生产的消息
     */
    private String message;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 队列
     */
    private String queue;

    /**
     * 绑定路由key
     */
    private String routingKey;

    /**
     * 重试次数
     */
    private Integer tryCount;

    /**
     * 消息消费的状态，true
     */
    private Boolean consumeStatus;

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
    private String exchangeType;
}
