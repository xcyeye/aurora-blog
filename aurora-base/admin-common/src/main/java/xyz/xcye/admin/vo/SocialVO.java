package xyz.xcye.admin.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(title="社交")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialVO implements Serializable {
    /**
    * 唯一uid,自增
    */
    @Schema(title = "唯一uid,自增")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**
    * 社交名称
    */
    @Schema(title = "社交名称")
    private String socialName;

    /**
    * 此社交图标的地址
    */
    @Schema(title = "此社交图标的地址")
    private String socialIcon;

    /**
    * 此社交的链接
    */
    @Schema(title = "此社交的链接")
    private String socialUrl;

    /**
    * 1： 显示此社交 0： 不显示
    */
    @Schema(title = "1： 显示此社交 0： 不显示")
    private Boolean show;

    /**
    * 此社交属于哪个用户
    */
    @Schema(title = "此社交属于哪个用户")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userUid;

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

    /**
    * 1: 删除 0：不删除
    */
    @Schema(title = "1: 删除 0：不删除")
    private Boolean delete;

    private static final long serialVersionUID = 1L;
}