package xyz.xcye.message.po;

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
 * @table email_log <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "email_log数据表的实体类")
public class EmailLog implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     * 唯一uid
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "唯一uid")
    private Long uid;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "用户uid")
    private Long userUid;

    /**
     * 发送者的邮箱号
     */
    @Schema(title = "发送者的邮箱号")
    private String sender;

    /**
     * 标题
     */
    @Schema(title = "标题")
    private String subject;

    /**
     * 内容
     */
    @Schema(title = "内容")
    private String content;

    /**
     * 接受者的邮箱号
     */
    @Schema(title = "接受者的邮箱号")
    private String receiver;

    /**
     * 1:发送成功0：没有发送成功
     */
    @Schema(title = "1:发送成功0：没有发送成功")
    private Boolean send;

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