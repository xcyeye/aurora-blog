package xyz.xcye.message.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.xcye.message.po.MailTemplate;

/**
 * @description TODO <br/>
 * @date 2022-12-14 22:01:53 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "mail_template数据表的VO")
public class MailTemplateVO extends MailTemplate {

}