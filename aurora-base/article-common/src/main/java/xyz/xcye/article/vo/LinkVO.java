package xyz.xcye.article.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;

@Schema(title="友情链接")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkVO implements Serializable {
    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
     * 此条友情链接是哪个用户的
     */
    @Schema(title = "此条友情链接是哪个用户的")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userUid;

    /**
     * 此条友情链接属于哪个分类
     */
    @Schema(title = "此条友情链接属于哪个分类")
    @JsonSerialize(using = ToStringSerializer.class)
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
    @Schema(title = "此友情链接对应的站长的qq号")
    private Long qqNumber;

    /**
    * 创建时间
    */
    @Schema(title = "创建时间")
    @Null
    private String createTime;

    /**
    * 最后更新时间
    */
    @Schema(title = "最后更新时间")
    private String updateTime;

    private static final long serialVersionUID = 1L;
}