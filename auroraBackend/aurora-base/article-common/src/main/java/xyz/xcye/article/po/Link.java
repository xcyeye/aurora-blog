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
 * @table link <br/>
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "link数据表的实体类")
public class Link implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 此条友情链接是哪个用户的
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "此条友情链接是哪个用户的")
	private Long userUid;

	/**
	 * 此条友情链接属于哪个分类
	 */
	@Schema(title = "此条友情链接属于哪个分类")
	private String categoryName;

	/**
	 * logo地址
	 */
	@Schema(title = "logo地址")
	private String linkLogo;

	/**
	 * 链接地址
	 */
	@Schema(title = "链接地址")
	private String linkUrl;

	/**
	 * 对方的名称
	 */
	@Schema(title = "对方的名称")
	private String linkTitle;

	/**
	 * 描述信息
	 */
	@Schema(title = "描述信息")
	private String linkDescription;

	/**
	 * 对方站点的封面图
	 */
	@Schema(title = "对方站点的封面图")
	private String linkCover;

	/**
	 * 是否展示此条友情链接 1：展示 0：不展示
	 */
	@Schema(title = "是否展示此条友情链接 1：展示 0：不展示")
	private Boolean publish;

	/**
	 * 此友情链接对应的站长邮箱
	 */
	@Schema(title = "此友情链接对应的站长邮箱")
	private String email;

	/**
	 * 此友情链接对应的站长的qq号
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "此友情链接对应的站长的qq号")
	private Long qqNumber;

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