package xyz.xcye.message.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xcye <br/>
 * @description mail_template数据表的POJO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplatePojo {

    /**
     * 唯一uid
     */
    @Schema(title = "唯一uid")
    private Long uid;

    /**
     * 邮件发送模板的html
     */
    @Schema(title = "邮件发送模板的html")
    private String template;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private String createTime;

    /**
     * 最后修改时间
     */
    @Schema(title = "最后修改时间")
    private String updateTime;

    /**
     * 邮件默认发送标题，如果没有传入的话
     */
    @Schema(title = "邮件默认发送标题，如果没有传入的话")
    private String subject;

    /**
     * 此模板是哪个用户创建的
     */
    @Schema(title = "此模板是哪个用户创建的")
    private Long userUid;

    /**
     * 此模板是回复评论，还是收到评论等
     */
    @Schema(title = "此模板是回复评论，还是收到评论等")
    private String typeName;

}