package xyz.xcye.file.po;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table au_people <br/>
 * @description TODO <br/>
 * @date 2022-12-13 18:59:12 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "au_people数据表的实体类")
public class AuPeople implements Serializable {

	/**
	 * key
	 */
	@Schema(title = "key")
	private Integer uid;

	/**
	 * 
	 */
	@Schema(title = "")
	private String name;

}