package xyz.xcye.admin.po;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table site_setting <br/>
 * @description 前台网站设置 <br/>
 * @date 2023-01-04 20:42:58 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "site_setting数据表的实体类")
public class SiteSetting implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long uid;

	/**
	 * 用户uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "用户uid")
	private Long userUid;

	/**
	 * 参数名称
	 */
	@Schema(title = "参数名称")
	private String paramName;

	/**
	 * 参数值
	 */
	@Schema(title = "参数值")
	private String paramValue;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private String createTime;

	/**
	 * 最后更新时间
	 */
	@Schema(title = "最后更新时间")
	private String updateTime;

}