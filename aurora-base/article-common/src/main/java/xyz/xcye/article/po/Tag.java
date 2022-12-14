package xyz.xcye.article.po;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table tag <br/>
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "tag数据表的实体类")
public class Tag implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 此标签的标题
	 */
	@Schema(title = "此标签的标题")
	private String title;

	/**
	 * 此标签的简介
	 */
	@Schema(title = "此标签的简介")
	private String summary;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private String createdTime;

	/**
	 * 最后修改时间
	 */
	@Schema(title = "最后修改时间")
	private String updatedTime;

	/**
	 * 此类别的封面图uid
	 */
	@Schema(title = "此类别的封面图uid")
	private String coverUrl;

	/**
	 * 1: 删除，0：未删除
	 */
	@Schema(title = "1: 删除，0：未删除")
	private Boolean delete;

	/**
	 * 用户的userUid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "用户的userUid")
	private Long userUid;

}