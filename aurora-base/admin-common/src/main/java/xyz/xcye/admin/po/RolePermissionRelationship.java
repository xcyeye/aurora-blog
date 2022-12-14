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
 * @table role_permission <br/>
 * @description TODO <br/>
 * @date 2022-12-13 21:00:16 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "role_permission数据表的实体类")
public class RolePermissionRelationship implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long uid;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long roleUid;

	/**
	 * 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "")
	private Long permissionUid;

}