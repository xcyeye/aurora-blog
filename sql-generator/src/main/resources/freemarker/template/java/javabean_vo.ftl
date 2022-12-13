package ${javaClassDefinition.VO.fullPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.v3.oas.annotations.media.Schema;
import ${javaClassDefinition.PO.fullPackage}.${javaClassDefinition.PO.className};

/**
 * @description TODO <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "${tableInfo.tableName}数据表的VO")
public class ${javaClassDefinition.VO.className} extends ${javaClassDefinition.PO.className} {

}