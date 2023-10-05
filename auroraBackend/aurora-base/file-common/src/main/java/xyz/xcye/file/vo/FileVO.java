package xyz.xcye.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.xcye.file.po.File;

/**
 * @author xcye <br/>
 * @description TODO <br/>
 * @date 2022-12-14 22:31:22 <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "file数据表的VO")
public class FileVO extends File {
    /**
     * 文件路径部分的uri
     */
    private String filePathUri;
}