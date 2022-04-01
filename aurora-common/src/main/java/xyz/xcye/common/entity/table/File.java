package xyz.xcye.common.entity.table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.common.enums.FieldLengthEnum;
import xyz.xcye.common.valid.Delete;
import xyz.xcye.common.valid.Update;
import xyz.xcye.common.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_file
 * @author qsyyke
 */

@ApiModel(value = "文件数据表映射实体",description = "映射数据表中的字段")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {

    /**
     * 唯一uid 不能为null 主键
     */
    @ApiModelProperty(value = "唯一uid")
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 文件创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ApiModelProperty(value = "创建时间")
    //@ValidateString(value = "文件-创建的时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 此文件的删除状态 true：已删除 false：未删除
     */
    @ApiModelProperty(value = "删除状态")
    private boolean deleteStatus;

    /**
     * 此文件的名字，含后缀
     * <p>length < 120</p>
     */
    @ApiModelProperty(value = "文件名称")
    //@ValidateString(value = "文件-名称",max = FieldLengthEnum.FILE_NAME)
    private String fileName;

    /**
     * 文件的大小 字节单位
     */
    @ApiModelProperty(value = "文件大小byte")
    private long size;

    /**
     * 此文件的删除时间 可以为null
     * <p>mysql -> datetime</p>
     */
    @ApiModelProperty(value = "删除时间")
    private String deletedAt;

    /**
     * 此文件的简介 可以为null
     * <p>length < 500</p>
     */
    @ApiModelProperty(value = "文件简介")
    @Length(max = FieldLengthEnum.SUMMARY,message = "文件-简介不能超过{max}")
    private String summary;

    /**
     * 此文件的访问路径
     */
    @ApiModelProperty(value = "文件路径")
    @Length(max = FieldLengthEnum.FILE_PATH,message = "文件的访问路径长度不能超过{max}")
    private String path;

    /**
     * 文件的存储模式
     */
    @ApiModelProperty(value = "文件存储模式 0：本地存储")
    private int storageMode;

    /**
     * 此文件的存储位置
     */
    @Length(message = "文件存储路径长度不能超过{max}",max = FieldLengthEnum.FILE_PATH)
    private String storagePath;
}
