package xyz.xcye.message.vo;

import lombok.Data;

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
     * 如果发生错误，则错误消息是什么
     */
    private String errorMessage;

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
    private String exchangeType;
}
