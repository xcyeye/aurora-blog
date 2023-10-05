package xyz.xcye.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table role <br/>
 * @description TODO <br/>
 * @date 2022-12-13 21:00:16 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "role数据表的实体类")
public class Role implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid，自增
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid，自增")
    private Long uid;

    /**
     * 角色的名称，不用添加ROLE_
     */
    @Schema(title = "角色的名称，不用添加ROLE_")
    private String name;

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
     * 用户的状态 1：已禁用 0：未禁用
     */
    @Schema(title = "用户的状态 1：已禁用 0：未禁用")
    private Boolean status;

}