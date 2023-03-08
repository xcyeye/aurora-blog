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
 * @table mail_template <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "mail_template数据表的实体类")
public class MailTemplate implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 邮件发送模板的html
	 */
	@Schema(title = "邮件发送模板的html")
	private String template;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private String createTime;

	/**
	 * 最后修改时间
	 */
	@Schema(title = "最后修改时间")
	private String updateTime;

	/**
	 * 邮件默认发送标题，如果没有传入的话
	 */
	@Schema(title = "邮件默认发送标题，如果没有传入的话")
	private String subject;

	/**
	 * 此模板是哪个用户创建的
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "此模板是哪个用户创建的")
	private Long userUid;

	/**
	 * 此模板是回复评论，还是收到评论等
	 */
	@Schema(title = "此模板是回复评论，还是收到评论等")
	private String typeName;

}