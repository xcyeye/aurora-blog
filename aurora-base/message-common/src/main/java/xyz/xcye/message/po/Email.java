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
 * @table email <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "email数据表的实体类")
public class Email implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 此条记录和用户表中的某个用户对应
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "此条记录和用户表中的某个用户对应")
	private Long userUid;

	/**
	 * 发送者邮件的主机
	 */
	@Schema(title = "发送者邮件的主机")
	private String emailHost;

	/**
	 * 发送者邮件的密码，或者授权码
	 */
	@Schema(title = "发送者邮件的密码，或者授权码")
	private String emailPassword;

	/**
	 * 发送者的协议
	 */
	@Schema(title = "发送者的协议")
	private String protocol;

	/**
	 * 邮箱号
	 */
	@Schema(title = "邮箱号")
	private String email;

	/**
	 * 此邮件发送的端口
	 */
	@Schema(title = "此邮件发送的端口")
	private Integer port;

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

}