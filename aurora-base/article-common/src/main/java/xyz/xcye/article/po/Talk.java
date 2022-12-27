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
 * @table talk <br/>
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "talk数据表的实体类")
public class Talk implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 发布此说说的用户uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "发布此说说的用户uid")
	private Long userUid;

	/**
	 * 此说说是否显示评论 1： 显示 0： 不显示
	 */
	@Schema(title = "此说说是否显示评论 1： 显示 0： 不显示")
	private Boolean showComment;

	/**
	 * 此说说的评论uid集合
	 */
	@Schema(title = "此说说的评论uid集合")
	private String commentUids;

	/**
	 * 最后更新时间
	 */
	@Schema(title = "最后更新时间")
	private String updateTime;

	/**
	 * 此说说发布时间
	 */
	@Schema(title = "此说说发布时间")
	private String createTime;

	/**
	 * 此说说的内容
	 */
	@Schema(title = "此说说的内容")
	private String content;

	/**
	 * 此说说标题
	 */
	@Schema(title = "此说说标题")
	private String title;

	/**
	 * 1： 显示说说，0： 不显示说说
	 */
	@Schema(title = "1： 显示说说，0： 不显示说说")
	private Boolean show;

	/**
	 * 此说说的点赞数
	 */
	@Schema(title = "此说说的点赞数")
	private Integer likeNumber;

	/**
	 * 此说说对应的图片uid集合
	 */
	@Schema(title = "此说说对应的图片uid集合")
	private String pictureUids;

	/**
	 * 1: 已删除
	 */
	@Schema(title = "1: 已删除")
	private Boolean delete;

}