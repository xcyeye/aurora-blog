package xyz.xcye.article.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.article.po.Category;

/**
 * @author xcye <br/>
 * @description TODO <br/>
 * @date 2022-12-14 20:46:02 <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "category数据表的VO")
public class CategoryVO extends Category {

}