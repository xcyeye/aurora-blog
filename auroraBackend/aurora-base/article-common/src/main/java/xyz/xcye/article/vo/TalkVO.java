package xyz.xcye.article.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.xcye.article.po.Talk;

/**
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "talk数据表的VO")
public class TalkVO extends Talk {

}