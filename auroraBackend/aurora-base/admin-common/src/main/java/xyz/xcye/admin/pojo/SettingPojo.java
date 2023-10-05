package xyz.xcye.admin.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xcye <br/>
 * @description setting数据表的POJO <br/>
 * @date 2022-12-30 15:46:26 <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingPojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 参数编码
     */
    @Schema(title = "参数编码")
    private String paramCode;

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