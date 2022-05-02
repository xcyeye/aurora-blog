package xyz.xcye.file.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import xyz.xcye.core.constant.FieldLengthConstant;

import java.io.Serializable;

/**
 * 数据表 au_file
 * @author qsyyke
 */

@Data
public class FileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一uid 不能为null 主键
     */
    private Long uid;

    /**
     * 此文件是否删除
     */
    private Boolean delete;

    /**
     * 文件创建时间 不能为null
     * <p>mysql -> datetime</p>
     */
    private String createTime;

    /**
     * 此文件的名字，含后缀
     * <p>length < 120</p>
     */
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
    private String summary;

    /**
     * 此文件的访问路径
     */
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
