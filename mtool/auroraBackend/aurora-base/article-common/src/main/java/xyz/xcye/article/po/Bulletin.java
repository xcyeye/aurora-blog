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
 * @table bulletin <br/>
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "bulletin数据表的实体类")
public class Bulletin implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 公告的标题
	 */
	@Schema(title = "公告的标题")
	private String title;

	/**
	 * 公告内容
	 */
	@Schema(title = "公告内容")
	private String content;

	/**
	 * 公告创建时间
	 */
	@Schema(title = "公告创建时间")
	private String createTime;

	/**
	 * 公告最后修改时间
	 */
	@Schema(title = "公告最后修改时间")
	private String updateTime;

	/**
	 * 发布此公告的用户uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "发布此公告的用户uid")
	private Long userUid;

	/**
	 * 1: 显示公告 0： 不显示该公告
	 */
	@Schema(title = "1: 显示公告 0： 不显示该公告")
	private Boolean show;

	/**
	 * 1：定时发布 0： 不定时发布公告
	 */
	@Schema(title = "1：定时发布 0： 不定时发布公告")
	private Boolean timing;

	/**
	 * 定时发布公告的时间
	 */
	@Schema(title = "定时发布公告的时间")
	private String timingPublishTime;

	/**
	 * 1:删除 0：未删除
	 */
	@Schema(title = "1:删除 0：未删除")
	private Boolean delete;

}