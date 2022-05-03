package xyz.xcye.file.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;
import xyz.xcye.core.valid.Delete;
import xyz.xcye.core.valid.Update;
import xyz.xcye.core.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据表 au_file
 * @author qsyyke
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private Long uid;

    /**
     * 文件创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    //@ValidateString(value = "文件-创建的时间",max = FieldLengthConstant.TIME_FORMAT)
    private String createTime;

    /**
     * 此文件的删除状态 true：已删除 false：未删除
     */
    private Boolean delete;

    /**
     * 此文件的名字，含后缀
     * <p>length < 120</p>
     */
    //@ValidateString(value = "文件-名称",max = FieldLengthConstant.FILE_NAME)
    private String fileName;

    /**
     * 文件的大小 字节单位
     */
    private Long size;

    /**
     * 此文件的删除时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String deleteTime;

    /**
     * 此文件的简介 可以为null
     * <p>length < 500</p>
     */
    @ValidateString(value = "文件，文件简介",max = FieldLengthConstant.SUMMARY)
    private String summary;

    /**
     * 此文件的访问路径
     */
    @Length(max = FieldLengthConstant.FILE_PATH,message = "文件的访问路径长度不能超过{max}")
    private String path;

    /**
     * 文件的存储模式
     */
    private Integer storageMode;

    /**
     * 此文件的存储位置
     */
    @Length(message = "文件存储路径长度不能超过{max}",max = FieldLengthConstant.FILE_PATH)
    private String storagePath;
}
