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
 * @table permission <br/>
 * @description TODO <br/>
 * @date 2022-12-13 21:00:16 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "permission数据表的实体类")
public class Permission implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid，自增
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid，自增")
    private Long uid;

    /**
     * 权限的名称
     */
    @Schema(title = "权限的名称")
    private String name;

    /**
     * 权限的地址，可以是组件的名称
     */
    @Schema(title = "权限的地址，可以是组件的名称")
    private String path;

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