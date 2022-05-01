package xyz.xcye.common.entity.amqp;

import lombok.Data;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author qsyyke
 */

@Data
public class ConfirmReturnEntity {
    /** 回调相关数据 **/
    CorrelationData correlationData;

    /** true表示应答 **/
    boolean ack;

    /** 如果出现异常，则回调的消息是什么 **/
    String cause;
}
