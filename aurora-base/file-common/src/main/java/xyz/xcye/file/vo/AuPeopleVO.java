package xyz.xcye.file.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.xcye.file.po.AuPeople;

/**
 * @description TODO <br/>
 * @date 2022-12-13 18:59:12 <br/>
 * @author xcye <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "au_people数据表的VO")
public class AuPeopleVO extends AuPeople {

}