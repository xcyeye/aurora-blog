package xyz.xcye.message.po;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table message_log <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "message_log数据表的实体类")
public class MessageLog implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 投递的消息
	 */
	@Schema(title = "投递的消息")
	private String message;

	/**
	 * 交换机名称
	 */
	@Schema(title = "交换机名称")
	private String exchange;

	/**
	 * 队列名称
	 */
	@Schema(title = "队列名称")
	private String queue;

	/**
	 * 绑定路由key
	 */
	@Schema(title = "绑定路由key")
	private String routingKey;

	/**
	 * 重试次数
	 */
	@Schema(title = "重试次数")
	private Integer tryCount;

	/**
	 * 1: 表示消费成功 0：表示消费失败
	 */
	@Schema(title = "1: 表示消费成功 0：表示消费失败")
	private Boolean consumeStatus;

	/**
	 * 
	 */
	@Schema(title = "")
	private String createTime;

	/**
	 * 
	 */
	@Schema(title = "")
	private String updateTime;

	/**
	 * 交换机类型
	 */
	@Schema(title = "交换机类型")
	private String exchangeType;

	/**
	 * 确认状态 1：应答了
	 */
	@Schema(title = "确认状态 1：应答了")
	private Boolean ackStatus;

	/**
	 * 如果发生错误，则错误消息是什么
	 */
	@Schema(title = "如果发生错误，则错误消息是什么")
	private String errorMessage;

}