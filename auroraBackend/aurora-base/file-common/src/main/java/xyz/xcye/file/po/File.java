package xyz.xcye.file.po;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table file <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:31:22 <br/>
 * @author xcye <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "file数据表的实体类")
public class File implements Serializable {

private static final long serialVersionUID = 13247652346523L;

	/**
	 * 唯一uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "唯一uid")
	private Long uid;

	/**
	 * 用户uid
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "用户uid")
	private Long userUid;

	/**
	 * 此文件的名称
	 */
	@Schema(title = "此文件的名称")
	private String fileName;

	/**
	 * 此文件的大小 字节为单位
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@Schema(title = "此文件的大小 字节为单位")
	private Long size;

	/**
	 * 此文件的简介
	 */
	@Schema(title = "此文件的简介")
	private String summary;

	/**
	 * 此文件的存放路径，如果是对象存储，则表示objectName
	 */
	@Schema(title = "此文件的存放路径，如果是对象存储，则表示objectName")
	private String path;

	/**
	 * 文件存储的模式
	 */
	@Schema(title = "文件存储的模式")
	private Integer storageMode;

	/**
	 * 存储的路径
	 */
	@Schema(title = "存储的路径")
	private String storagePath;

	/**
	 * 1.: 已经删除
	 */
	@Schema(title = "1.: 已经删除")
	private Boolean delete;

	/**
	 * 上传此文件的时间
	 */
	@Schema(title = "上传此文件的时间")
	private String createTime;

	/**
	 * 最后删除时间
	 */
	@Schema(title = "最后删除时间")
	private String deleteTime;

}