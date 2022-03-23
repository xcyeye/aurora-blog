package xyz.xcye.entity.table;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.enums.FieldLengthEnum;
import xyz.xcye.valid.Delete;
import xyz.xcye.valid.Update;
import xyz.xcye.valid.validator.Status;
import xyz.xcye.valid.validator.ValidateString;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * 数据表 au_file
 * @author qsyyke
 */

@Data
public class File {

    /**
     * 唯一uid 不能为null 主键
     */
    @NotNull(groups = {Delete.class, Update.class})
    private BigInteger uid;

    /**
     * 文件创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    @ValidateString(value = "文件-创建的时间",max = FieldLengthEnum.TIME_FORMAT)
    private String createdAt;

    /**
     * 此文件的删除状态 1：已删除 0：未删除
     */
    @Status(value = "文件-删除")
    private int deleteStatus;

    /**
     * 此文件的名字，含后缀
     * <p>length < 120</p>
     */
    @ValidateString(value = "文件-名称",max = FieldLengthEnum.FILE_NAME)
    private String fileName;

    /**
     * 文件的大小 字节单位
     */
    private long size;

    /**
     * 此文件的删除时间 可以为null
     * <p>mysql -> datetime</p>
     */
    private String deleteAt;

    /**
     * 此文件的简介 可以为null
     * <p>length < 500</p>
     */
    @Length(max = FieldLengthEnum.SUMMARY,message = "文件-简介不能超过{max}")
    private String summary;

    /**
     * 此文件的存放路径 不能为null 如果是对象存储，则表示objectName
     */
    @ValidateString(value = "文件-路径",max = FieldLengthEnum.FILE_PATH)
    private String path;
}
